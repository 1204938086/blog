package com.wyj.blog;

import com.wyj.blog.pojo.Article;
import com.wyj.blog.service.ArticleService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
public class ArticleTest {
    @Autowired
    private ArticleService articleService;

    private ObjectId articleId = new ObjectId("6245568ff37b2f218f397d82");

    /**
     * 测试用例Article_01
     * 创建新文章
     * 预期结果：创建成功
     * @throws ParseException
     */
    @Test
    public void Article_01() throws ParseException {
        Article article = new Article();
        article.setId(articleId);
        article.setTitle("测试");
        article.setType("测试分类");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("测试标签");
        article.setTag(tags);
        article.setCreateDate(new Date());
        article.setModifyDate(new Date());
        article.setMarkdown("测试内容");
        article.setHit(0);
        assert articleService.createArticle(article) != null;
    }
    /**
     * 测试用例Article_02
     * 删除文章
     * 预期结果：删除成功
     */
    @Test
    public void Article_02() {
        assert articleService.removeArticle(articleId);
    }
}
