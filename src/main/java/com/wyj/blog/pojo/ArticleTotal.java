package com.wyj.blog.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 统计数据总计对象
 */
@Document("total")
@Data
public class ArticleTotal {
    private String type;
    private Long hit;        // 点击量
    private Long visitor;    // 访问人数
    private Long commit;     // 总提交次数
    private Long article;    // 文章数量
    private Long tags;       // 标签数量
    private Long types;      // 分类数量
    private Long comments;   // 评论数量
}
