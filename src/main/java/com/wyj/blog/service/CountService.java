package com.wyj.blog.service;

import com.wyj.blog.pojo.*;
import com.wyj.blog.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class CountService {
    @Autowired
    private MongoTemplate mongo;

    /**
     * 获取标签数量
     * @return
     */
    private Long countTags(){
        return  mongo.count(new Query(), Tag.class);
    }

    /**
     * 获取类型数量
     */
    private Long countTypes(){
        return  mongo.count(new Query(), Type.class);
    }
    /**
     * 获取文章数量
     */
    private Long countArticle(){
        return  mongo.count(new Query(), Article.class);
    }

    /**
     * 检查是否存在了总统计数据，如果不存在则创建
     */
    private void checkTotalExist(){
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("article"));
        ArticleTotal total = mongo.findOne(query, ArticleTotal.class);
        if (total == null){
            ArticleTotal articleTotal = new ArticleTotal();
            articleTotal.setType("article");
            articleTotal.setHit(0L);
            articleTotal.setVisitor(0L);
            articleTotal.setTags(countTags());
            articleTotal.setTypes(countTypes());
            articleTotal.setArticle(countArticle());
            mongo.save(articleTotal);
        }
    }

    /**
     * 检查今天的统计数据是否存在，不存在则创建
     * @throws ParseException
     */
    private void checkTodayExist() throws ParseException {
        Date toDayDate = DateUtils.getToDayDate();
        Query queryToDayHit = new Query();
        queryToDayHit.addCriteria(Criteria.where("date").is(toDayDate));
        ArticleDateData articleDateData = mongo.findOne(queryToDayHit, ArticleDateData.class);
        if (articleDateData == null){
            ArticleDateData dateData = new ArticleDateData();
            dateData.setDate(toDayDate);
            dateData.setHit(0);
            dateData.setVisitor(0);
            mongo.save(dateData);
        }
    }

    /**
     * 增加提交数量
     * @throws ParseException
     */
    public void addCommitCount() throws ParseException {
        // 统计今日点击量
        Date toDayDate = DateUtils.getToDayDate();
        Query queryToDayCommit = new Query();
        queryToDayCommit.addCriteria(Criteria.where("date").is(toDayDate));
        CommitCount commitCount = mongo.findOne(queryToDayCommit, CommitCount.class);
        if (commitCount != null){  // 验证是否存在今天的数据了，存在则增加提交数量
            Update updateToDayCommit = new Update();
            updateToDayCommit.inc("commit");
            mongo.updateFirst(queryToDayCommit,updateToDayCommit,CommitCount.class);
        }else { // 不存在，创建数据
            CommitCount newCommit = new CommitCount();
            newCommit.setDate(toDayDate);
            newCommit.setCommit(1);
            mongo.save(newCommit);
        }
    };


    /**
     * 获取所有提交记录
     */
    public List<CommitCount> selectAllCommit(){
        return mongo.findAll(CommitCount.class);
    }
    /**
     * 获取总统计数据
     * @return
     */
    public ArticleTotal selectAllCount(){
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("article"));
        return  mongo.findOne(query, ArticleTotal.class);
    }


    /**
     * 增加今日点击量统计数据
     * @throws ParseException
     */
    public void addToDayHit() throws ParseException {
        checkTodayExist();
        // 统计今日点击量
        Date toDayDate = DateUtils.getToDayDate();
        Query queryToDayHit = new Query();
        queryToDayHit.addCriteria(Criteria.where("date").is(toDayDate));
        Update update = new Update();
        update.inc("hit");
        mongo.updateFirst(queryToDayHit,update,ArticleDateData.class);

    }
    /**
     * 增加今日访问量
     */
    public void addToDayVisitor() throws ParseException {
        checkTodayExist();
        Date toDayDate = DateUtils.getToDayDate();
        Query queryToDayHit = new Query();
        queryToDayHit.addCriteria(Criteria.where("date").is(toDayDate));
        Update update = new Update();
        update.inc("visitor");
        mongo.updateFirst(queryToDayHit,update,ArticleDateData.class);
    }


    /**
     * 增加总点击量统计数据
     */
    public void addTotalHit(){
        checkTotalExist();
        // 总点击量增加
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("article"));
        Update update = new Update();
        update.inc("hit");
        mongo.updateFirst(query,update, ArticleTotal.class);

    }
    /**
     * 增加总点击量统计数据
     */
    public void addTotalVisitor(){
        checkTotalExist();
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("article"));
        Update updateVisitor = new Update();
        updateVisitor.inc("visitor");
        mongo.updateFirst(query,updateVisitor, ArticleTotal.class);

    }

    /**
     * 增加总文章数量
     */
    public void addTotalArticle(){
        checkTotalExist();
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("article"));
        Update update = new Update();
        update.inc("article");
        mongo.updateFirst(query,update, ArticleTotal.class);
    }
    /**
     * 减少总文章数量
     */
    public void subTotalArticle(){
        checkTotalExist();
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("article"));
        Update update = new Update();
        update.inc("article",-1);
        mongo.updateFirst(query,update, ArticleTotal.class);
    }

    /**
     * 增加标签数量
     */
    public void addTotalTags(){
        checkTotalExist();
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("article"));
        Update update = new Update();
        update.inc("tags");
        mongo.updateFirst(query,update, ArticleTotal.class);
    }
    /**
     * 减少标签数量
     */
    public void subTotalTags(){
        checkTotalExist();
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("article"));
        Update update = new Update();
        update.inc("tags",-1);
        mongo.updateFirst(query,update, ArticleTotal.class);
    }


    /**
     * 增加分类数量
     */
    public void addTotalTypes(){
        checkTotalExist();
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("article"));
        Update update = new Update();
        update.inc("types");
        mongo.updateFirst(query,update, ArticleTotal.class);
    }
    /**
     * 减少分类数量
     */
    public void subTotalTypes(){
        checkTotalExist();
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("article"));
        Update update = new Update();
        update.inc("types",-1);
        mongo.updateFirst(query,update, ArticleTotal.class);
    }

    /**
     * 增加总提交次数
     */
    public void addTotalCommit(){
        checkTotalExist();
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("article"));
        Update update = new Update();
        update.inc("commit");
        mongo.updateFirst(query,update, ArticleTotal.class);
    }

    /**
     * 增加总评论数
     */
    public void addTotalComments(){
        checkTotalExist();
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("article"));
        Update update = new Update();
        update.inc("comments");
        mongo.updateFirst(query,update, ArticleTotal.class);
    }
    /**
     * 减少总评论数
     */
    public void subTotalComments(){
        checkTotalExist();
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("article"));
        Update update = new Update();
        update.inc("comments",-1);
        mongo.updateFirst(query,update, ArticleTotal.class);
    }

}
