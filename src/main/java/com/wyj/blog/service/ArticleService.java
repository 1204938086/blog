package com.wyj.blog.service;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.wyj.blog.param.ArticleQueryParam;
import com.wyj.blog.param.RemoveCommentParam;
import com.wyj.blog.pojo.Article;
import com.wyj.blog.pojo.ArticleDateData;
import com.wyj.blog.pojo.Comment;
import com.wyj.blog.utils.MarkdownUtils;
import org.bson.BasicBSONObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@Service
public class ArticleService {
    @Autowired
    private MongoTemplate mongo;

    @Autowired
    private CountService countService;

    /**
     * 创建文章
     * */
    public Article createArticle(Article article) throws ParseException {
        Article save = null;
        if (article != null){
            if (article.getId() == null) {
                article.setId(new ObjectId());
            }
            if (article.getTitle() != null && article.getMarkdown() != null){
                article.setHit(0);
                article.setCreateDate(new Date());
                article.setModifyDate(new Date());
                article.setContent(MarkdownUtils.MarkdownToHtmlExt(article.getMarkdown()));
                save = mongo.save(article);
                // 添加提交记录
                countService.addCommitCount();
                // 增加总文章数
                countService.addTotalArticle();
                countService.addTotalCommit();
            }
        }
        return save;
    }

    /**
     * 根据Id查询文章
     */
    public Article selectById(ObjectId objectId) throws ParseException {
        // 获取文章信息
        Article article = mongo.findById(objectId, Article.class);
        if(article != null){
            // 点击量+1
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(objectId));
            Update update = new Update();
            update.inc("hit");
            mongo.updateFirst(query,update,Article.class);
            // 今日点击量增加
            countService.addToDayHit();
            // 总点击量增加
            countService.addTotalHit();
        }
        return article;
    }

    /**
     * 分页查询
     * @param pagesize 每页的大小
     * @param page 当前页数
     * @return 文章数据
     */
    public List<Article> selectByPage(Integer pagesize,Integer page) throws ParseException {
        Query query = new Query();
        query.skip(pagesize * (page-1));
        query.limit(pagesize);
        query.fields().exclude("content");
        if(page == 1){
            countService.addToDayVisitor();
            countService.addTotalVisitor();
        }
        return mongo.find(query,Article.class);
    }

    /**
     * 查询点击量最高的5篇文章
     * @return
     */
    public List<Article> selectByTopHit(){
        Query query = new Query();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("hit");
        query.with(Sort.by(Sort.Direction.DESC,"hit"));
        query.limit(5);
        query.fields().include("_id").include("title");
        return mongo.find(query,Article.class);

    }

    /**
     * 查询最新的5篇文章
     * @return 文章列表
     */
    public List<Article> selectByNew() {
        Query query = new Query();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("hit");
        query.with(Sort.by(Sort.Direction.DESC,"createDate"));
        query.limit(5);
        query.fields().include("_id").include("title");
        return mongo.find(query,Article.class);
    }

    /**
     * 查询所有文章
     * @return
     */
    public List<Article> selectAllArticle() {
        List<Article> articleList = mongo.find(new Query(), Article.class);
        return articleList;
    }

    /**
     * 删除文章
     * @param id 要被删除的文章ID
     * @return 是否删除成功
     */
    public Boolean removeArticle(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        DeleteResult result = mongo.remove(query,Article.class);
        Boolean ret =  result.getDeletedCount() == 1;
        // 减少文章总数
        if (ret){
            countService.subTotalArticle();
        }
        return ret;
    }

    /**
     * 修改文章
     * @param article 文章对象
     * @return 是否修改成功
     */
    public Boolean modify(Article article) throws ParseException {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(article.getId()));
        Update update = new Update();
        update.set("title",article.getTitle());
        update.set("markdown",article.getMarkdown());
        update.set("modifyDate",new Date());
        update.set("type",article.getType());
        update.set("tag",article.getTag());
        UpdateResult updateResult = mongo.updateFirst(query, update, Article.class);

        // 增加修改记录
        countService.addCommitCount();
        countService.addTotalCommit();
        return updateResult.getModifiedCount() == 1;
    }

    /**
     * 查询文章
     * @param param 查询参数
     * @return 查询结果
     */
    public List<Article> query(ArticleQueryParam param) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (param.getType()==null && param.getTitleOrContent()==null && param.getTag() == null){
            return new ArrayList<Article>();
        }else if (param.getType() != null){
            criteria = Criteria.where("type").is(param.getType());
        }else if (param.getTag() != null){
            criteria = Criteria.where("tag").is(param.getTag());
        }else if (param.getTitleOrContent() != null){
            criteria.orOperator(Criteria.where("markdown").regex(param.getTitleOrContent()),
                    Criteria.where("title").regex(param.getTitleOrContent()));
        }
        query.addCriteria(criteria);
        return mongo.find(query,Article.class);
    }

    /**
     * 查询所有的每日统计数据
     * @return
     */
    public List<ArticleDateData> selectAllDateData(){
        return mongo.find(new Query().with(Sort.by("date")),ArticleDateData.class);
    }

    /**
     * 创建评论
     * @param id        文章ID
     * @param comment
     * @return
     */
    public Boolean createComment(ObjectId id, Comment comment) {
        if (comment.getId() == null) {
            comment.setId(new ObjectId()); // 创建ID
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.push("comment",comment);
        UpdateResult updateResult = mongo.updateFirst(query, update, Article.class);
        countService.addTotalComments();
        return updateResult.getModifiedCount() == 1;
    }

    /**
     * 删除评论
     * @param articleId 文章ID
     * @param commentId 评论ID
     * @return 是否删除成功
     */
    public Boolean removeComment(ObjectId articleId,ObjectId commentId) {
        // 查找目标文章
        Query query = new Query().addCriteria(Criteria.where("_id").is(articleId));
        // 设置更新条件
        Update update = new Update();
        // 找到目标评论并删除
        update.pull("comment",new BasicBSONObject("_id",commentId));
        // 更新
        UpdateResult result = mongo.updateFirst(query, update, Article.class);
        boolean ret = result.getModifiedCount() == 1;
        if (ret){
            countService.subTotalComments();
        }
        return ret;
    }
}
