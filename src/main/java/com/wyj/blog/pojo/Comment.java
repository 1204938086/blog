package com.wyj.blog.pojo;

import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@ToString
public class Comment {
    @Id
    private ObjectId id;
    private String nickname;
    private String email;
    private String commentContent;
    private Date createTime;
}
