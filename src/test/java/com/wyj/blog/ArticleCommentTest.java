package com.wyj.blog;

import com.wyj.blog.pojo.Comment;
import com.wyj.blog.service.ArticleService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class ArticleCommentTest {
    @Autowired
    private ArticleService articleService;

    private ObjectId articleId = new ObjectId("623435eb2f85d97910c089d1");
    private ObjectId commentId = new ObjectId("62454ee0d22ad85d767f95d9");
    /**
     * 测试用例ArticleComment_01
     * 给文章"SpringBoot 学习笔记"添加一条评论
     * 预期结果：评论成功
     */
    @Test
    public void ArticleComment_01(){
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setNickname("测试人员");
        comment.setEmail("测试@test.com");
        comment.setCreateTime(new Date());
        comment.setCommentContent("测试评论");
        assert articleService.createComment(articleId,comment);
    }

    /**
     * 测试用例ArticleComment_02
     * 删除测试用例ArticleComment_01生成的评论
     * 预期结果：删除成功
     */
    @Test
    public void ArticleComment_02(){
        assert articleService.removeComment(articleId, commentId);
    }
}
