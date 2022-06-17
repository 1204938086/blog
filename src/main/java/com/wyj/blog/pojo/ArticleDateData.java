package com.wyj.blog.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("articledatedata")
@Data
public class ArticleDateData {
    private Date date;          // 日期
    private Integer hit;        // 点击量
    private Integer visitor;    // 访问人数
}
