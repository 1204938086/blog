package com.wyj.blog.service;

import com.wyj.blog.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    @Autowired
    private MongoTemplate mongo;
    @Autowired
    private CountService countService;

    /**
     * 检查是否已经存在该分类了
     * @param type 分类对象
     * @return 不存在返回true，存在返回false
     */
    private Boolean checkTypeExist(Type type){
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type.getType()));
        List<Type> types = mongo.find(query, Type.class);
        return types.size() == 0;
    }

    /**
     * 创建分类
     * @param type 分类对象
     * @return 创建标识
     *          1 : 成功
     *          0 ：已经存在
     *          -1 ： 创建失败
     */
    public Integer createType(Type type){
        if (checkTypeExist(type)){
            Type result = mongo.insert(type);
            if (result != null){
                countService.addTotalTypes();
            }
            return result != null ? 1:-1;
        }else {
            return 0;
        }
    }

    /**
     * 查询所有分类数据
     * @return 标签列表
     */
    public List<Type> selectAllType(){
        return mongo.find(new Query(),Type.class);
    }

    /**
     * 删除分类
     * @param type 分类对象
     * @return 是否删除成功
     */
    public Boolean removeType(Type type){
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type.getType()));
        Boolean ret = mongo.remove(query,Type.class).getDeletedCount() == 1;
        if (ret){
            countService.subTotalTypes();
        }
        return  ret;
    }


}
