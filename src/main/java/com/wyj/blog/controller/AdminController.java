package com.wyj.blog.controller;

import com.wyj.blog.param.RemoveCommentParam;
import com.wyj.blog.pojo.*;
import com.wyj.blog.service.ArticleService;
import com.wyj.blog.service.CountService;
import com.wyj.blog.service.TagService;
import com.wyj.blog.service.TypeService;
import com.wyj.blog.vo.BaseVO;
import com.wyj.blog.vo.BaseVoCode;
import com.wyj.blog.vo.CreateArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@CrossOrigin
@Controller
@RequestMapping("/admin")
@ResponseBody
public class AdminController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private CountService countService;


    @PostMapping("/createarticle")
    public CreateArticleVO createArticle(@RequestBody Article article) throws ParseException {
        CreateArticleVO vo = new CreateArticleVO();
        Article ret = articleService.createArticle(article);
        if (ret != null){
            vo.setSuccess(true)
                    .setCode(CreateArticleVO.CreateArticleCode.SUCCESS)
                    .setMessage("保存成功");
        }else {
            vo.setSuccess(false)
                    .setCode(CreateArticleVO.CreateArticleCode.ERROR)
                    .setMessage("保存失败");
        }
        return vo;
    }

    @PostMapping("/allarticle")
    public List<Article> allArticle(){
        return articleService.selectAllArticle();
    }

    @PostMapping("/removearticle")
    public BaseVO removeArticle(@RequestBody Article article){
        if (articleService.removeArticle(article.getId())){
            return new BaseVO()
                    .setSuccess(true)
                    .setCode(BaseVoCode.SUCCESS)
                    .setMessage("删除成功");
        }else {
            return new BaseVO()
                    .setSuccess(false)
                    .setCode(BaseVoCode.REMOVE_ARTICLE_ERROR)
                    .setMessage("删除失败");
        }
    }

    @PostMapping("/modifyarticle")
    public BaseVO modifyArticle(@RequestBody Article article) throws ParseException {
        if(articleService.modify(article)){
            return new BaseVO()
                    .setSuccess(true)
                    .setCode(BaseVoCode.SUCCESS)
                    .setMessage("修改成功");
        }else {
            return new BaseVO()
                    .setSuccess(false)
                    .setCode(BaseVoCode.MODIFY_ARTICLE_ERROR)
                    .setMessage("修改失败");
        }
    }

    @PostMapping("/article")
    public Article article(@RequestBody Article article) throws ParseException {
        return articleService.selectById(article.getId());
    }

    @PostMapping("/createtag")
    public BaseVO createTag(@RequestBody Tag tag){
        Integer result = tagService.createTag(tag);
        if(result == 1){
            return new BaseVO().setSuccess(true)
                    .setCode(BaseVoCode.SUCCESS)
                    .setMessage("创建成功");
        }else if(result == -1){
            return new BaseVO().setSuccess(false)
                    .setCode(BaseVoCode.CREATE_TAG_ERROR)
                    .setMessage("创建失败");
        }else {
            return new BaseVO().setSuccess(false)
                    .setCode(BaseVoCode.TAG_EXISTS)
                    .setMessage("标签已经存在");
        }
    }

    @PostMapping("/selectalltag")
    public List<Tag> selcetAllTag(){
        return tagService.selectAllTag();
    }

    @PostMapping("/removetag")
    public BaseVO removeTag(@RequestBody Tag tag){
        if(tagService.removeTag(tag)){
            return new BaseVO().setSuccess(true)
                    .setCode(BaseVoCode.SUCCESS)
                    .setMessage("删除成功");
        }else {
            return new BaseVO().setSuccess(false)
                    .setCode(BaseVoCode.REMOVE_TAG_ERROR)
                    .setMessage("删除失败");
        }
    }


    @PostMapping("/selectalltype")
    public List<Type> selectAllType(){
        return typeService.selectAllType();
    }
    @PostMapping("/createtype")
    public BaseVO createType(@RequestBody Type type){
        Integer result = typeService.createType(type);
        if(result == 1){
            return new BaseVO().setSuccess(true)
                    .setCode(BaseVoCode.SUCCESS)
                    .setMessage("创建成功");
        }else if(result == -1){
            return new BaseVO().setSuccess(false)
                    .setCode(BaseVoCode.CREATE_TYPE_ERROR)
                    .setMessage("创建失败");
        }else {
            return new BaseVO().setSuccess(false)
                    .setCode(BaseVoCode.TYPE_EXISTS)
                    .setMessage("类型已经存在");
        }
    }
    @PostMapping("/removetype")
    public BaseVO remoevType(@RequestBody Type type){
        if(typeService.removeType(type)){
            return new BaseVO().setSuccess(true)
                    .setCode(BaseVoCode.SUCCESS)
                    .setMessage("删除成功");
        }else {
            return new BaseVO().setSuccess(false)
                    .setCode(BaseVoCode.REMOVE_TYPE_ERROR)
                    .setMessage("删除失败");
        }
    }


    /**
     * 获取所有的每日统计信息数据
     * @return
     */
    @PostMapping("/alldate")
    public List<ArticleDateData> selectAllDateData(){
        return articleService.selectAllDateData();
    }

    /**
     * 获取所有提交记录
     * @return
     */
    @PostMapping("/allcommit")
    public List<CommitCount> selectAllCommitCount(){
        return countService.selectAllCommit();
    }

    /**
     * 获取总统计信息
     * @return
     */
    @PostMapping("/total")
    public ArticleTotal selectAllCount(){
        return countService.selectAllCount();
    }

    /**
     * 删除评论
     * @param param 请求参数
     *              articleId：文章ID
     *              commentId：评论ID
     * @return 执行结果
     */
    @PostMapping("/removecomment")
    public BaseVO removeComment(@RequestBody RemoveCommentParam param){
        BaseVO baseVO = new BaseVO();
        if (articleService.removeComment(param.getArticleId(),param.getCommentId())){
            baseVO.setSuccess(true)
                    .setCode(BaseVoCode.SUCCESS)
                    .setMessage("删除评论成功");
        }else {
            baseVO.setSuccess(false)
                    .setCode(BaseVoCode.REMOVE_COMMENT_ERR)
                    .setMessage("删除评论失败");
        }
        return baseVO;
    }
}
