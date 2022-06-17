package com.wyj.blog.controller;

import com.wyj.blog.param.ArticleQueryParam;
import com.wyj.blog.pojo.*;
import com.wyj.blog.service.ArticleService;
import com.wyj.blog.service.TagService;
import com.wyj.blog.service.TypeService;
import com.wyj.blog.vo.BaseVO;
import com.wyj.blog.vo.BaseVoCode;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequestMapping("/article")
//@Controller
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


    /**
     * 根据Id查询文章详情
     * @param id
     * @return
     */
    @GetMapping("/id/{id}")
    @ResponseBody
    public Article selectById(@PathVariable String id) throws ParseException {
        Article article = articleService.selectById(new ObjectId(id));
        return  article;
    }

    /**
     * 分页查询
     * @param pagesize  分页大小
     * @param page      页数
     * @return          文章数据
     */
    @GetMapping("/page/{pagesize}/{page}")
    @ResponseBody
    public List<Article> selectByPage(@PathVariable Integer pagesize, @PathVariable Integer page) throws ParseException {
        List<Article> articleList = articleService.selectByPage(pagesize,page);
        return  articleList;
    }

    /**
     * 获取访问最多的5篇文章
     */
    @GetMapping("/tophit")
    @ResponseBody
    public List<Article> selectByTopHit(){
        return articleService.selectByTopHit();
    }


    /**
     * 获取最新的5篇文章
     * @return
     */
    @GetMapping("/new")
    @ResponseBody
    public List<Article> selectByNew(){
        return articleService.selectByNew();
    }


    /**
     * 查询所有的分类信息
     * @return 分类信息列表
     */
    @GetMapping("/alltype")
    public List<Type> selectAllType(){
        return typeService.selectAllType();
    }

    /**
     * 查询所有的标签信息
     * @return 标签数据
     */
    @GetMapping("/alltag")
    public List<Tag> selectAllTag(){
        return tagService.selectAllTag();
    }

    /**
     * 根据条件查询信息
     */
    @PostMapping("/query")
    public List<Article> query(@RequestBody ArticleQueryParam param){
        return articleService.query(param);
    }

    @PostMapping("/createcomment/{id}")
    public BaseVO createComment(@PathVariable("id") ObjectId id,@RequestBody Comment comment){
        BaseVO ret = new BaseVO();
        if (articleService.createComment(id,comment)){
            ret.setSuccess(true)
                    .setCode(BaseVoCode.SUCCESS)
                    .setMessage("创建评论成功");
        }else {
            ret.setSuccess(false)
                    .setCode(BaseVoCode.CREATE_COMMENT_ERR)
                    .setMessage("创建评论失败");
        }
        return  ret;
    }

}
