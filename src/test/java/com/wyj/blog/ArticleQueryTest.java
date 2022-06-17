package com.wyj.blog;

import com.wyj.blog.param.ArticleQueryParam;
import com.wyj.blog.pojo.Article;
import com.wyj.blog.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ArticleQueryTest {
    @Autowired
    private ArticleService articleService;

    /**
     * 测试用例ArticleQuery_01
     * 查找存在的标签"Spring Boot"
     * 预期结果：成功查找到大于0条记录
     */
    @Test
    public void ArticleQuery_01(){
        ArticleQueryParam param = new ArticleQueryParam();
        param.setTag("SpringBoot");
        List<Article> queryResult = articleService.query(param);
        assert queryResult.size() > 0;
    }
    /**
     * 测试用例ArticleQuery_02
     * 查找不存在的标签"999"
     * 预期结果：查找到0条记录
     */
    @Test
    public void ArticleQuery_02(){
        ArticleQueryParam param = new ArticleQueryParam();
        param.setTag("999");
        List<Article> queryResult = articleService.query(param);
        assert queryResult.size() == 0;
    }
    /**
     * 测试用例ArticleQuery_03
     * 查找存在的分类"Java"
     * 预期结果：成功查找到大于0条记录
     */
    @Test
    public void ArticleQuery_03(){
        ArticleQueryParam param = new ArticleQueryParam();
        param.setType("Java");
        List<Article> queryResult = articleService.query(param);
        assert queryResult.size() > 0;
    }
    /**
     * 测试用例ArticleQuery_04
     * 查找文章内容或文章标题中包含"Java"的文章
     * 预期结果：成功查找到大于0条记录
     */
    @Test
    public void ArticleQuery_04(){
        ArticleQueryParam param = new ArticleQueryParam();
        param.setTitleOrContent("Java");
        List<Article> queryResult = articleService.query(param);
        System.out.println(queryResult);
        assert queryResult.size() > 0;
    }
}
