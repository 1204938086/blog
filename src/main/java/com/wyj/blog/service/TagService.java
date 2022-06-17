package com.wyj.blog.service;

import com.wyj.blog.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private MongoTemplate mongo;
    @Autowired
    private CountService countService;

    /**
     * 检查是否已经存在该标签了
     * @param tag 标签对象
     * @return 不存在返回true，存在返回false
     */
    private Boolean checkTagExist(Tag tag){
        Query query = new Query();
        query.addCriteria(Criteria.where("tag").is(tag.getTag()));
        List<Tag> tags = mongo.find(query, Tag.class);
        return tags.size() == 0;
    }

    /**
     * 创建新标签
     * @param tag 标签对象
     * @return 创建结果
     *          1 : 成功
     *          0 ：已经存在
     *          -1：失败
     */
    public Integer createTag(Tag tag){
        if (checkTagExist(tag)){
            Tag result = mongo.insert(tag);
            if (result != null){
                countService.addTotalTags();
            }
            return result != null ? 1:-1;
        }else {
            return 0;
        }
    }

    /**
     * 查询所有标签数据
     * @return 标签列表
     */
    public List<Tag> selectAllTag(){
        return mongo.find(new Query(),Tag.class);
    }

    /**
     * 删除标签
     * @param tag 标签对象
     * @return 删除是否成功
     */
    public Boolean removeTag(Tag tag){
        Query query = new Query();
        query.addCriteria(Criteria.where("tag").is(tag.getTag()));
        Boolean ret = mongo.remove(query,Tag.class).getDeletedCount() == 1;
        if (ret){
            countService.subTotalTags();
        }
        return  ret;
    }


}
