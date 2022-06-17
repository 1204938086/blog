package com.wyj.blog;

import com.wyj.blog.pojo.Tag;
import com.wyj.blog.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TagTest {
    @Autowired
    private TagService tagService;

    /**
     * 测试用例Tag_01
     * 创建新标签
     * 预期结果：创建成功(返回值1)
     */
    @Test
    public void Tag_01(){
        Tag tag = new Tag();
        tag.setTag("测试标签");
        assert  tagService.createTag(tag) == 1;
    }
    /**
     * 测试用例Tag_02
     * 删除Tag_01中创建的标签
     * 预期结果：删除成功
     */
    @Test
    public void Tag_02(){
        Tag tag = new Tag();
        tag.setTag("测试标签");
        assert  tagService.removeTag(tag);
    }

    /**
     * 测试用例Tag_03
     * 创建已经存在的标签SpringBoot
     * 预期结果：创建失败(返回值0)
     */
    @Test
    public void Tag_03(){
        Tag tag = new Tag();
        tag.setTag("SpringBoot");
        assert  tagService.createTag(tag) == 0;
    }
}
