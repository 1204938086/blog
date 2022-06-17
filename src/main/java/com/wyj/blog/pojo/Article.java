package com.wyj.blog.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * 文章类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("blog")
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) // 已经废弃
@JsonInclude(JsonInclude.Include.NON_NULL) // 只返回不为Null的值
public class Article {
    @Id
    private ObjectId id; // Id
    private String title; // 文章标题
    private String content; // 解析后文章内容
    private String markdown; // Markdown文本
    private String type; // 文章分类
    private List<String> tag; // 标签列表
    private Integer hit; // 点击数
    private Date createDate; // 创建时间
    private Date modifyDate; // 修改日期
    private List<Comment> comment; // 评论
}
