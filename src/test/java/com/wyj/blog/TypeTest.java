package com.wyj.blog;

import com.wyj.blog.pojo.Type;
import com.wyj.blog.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TypeTest {
    @Autowired
    private TypeService typeService;

    /**
     * 测试用例Type_01
     * 创建分类
     * 预期结果：创建成功(返回值1)
     */
    @Test
    public void Type_01(){
        Type type = new Type();
        type.setType("测试分类");
        assert typeService.createType(type)==1;
    }
    /**
     * 测试用例Type_02
     * 删除Type_01中创建的分离
     * 预期结果：删除成功
     */
    @Test
    public void Type_02(){
        Type type = new Type();
        type.setType("测试分类");
        assert typeService.removeType(type);
    }
    /**
     * 测试用例Type_03
     * 创建已经存在的分类Java
     * 预期结果：创建失败(返回值0)
     */
    @Test
    public void Type_03(){
        Type type = new Type();
        type.setType("Java");
        assert typeService.createType(type)==0;
    }
}
