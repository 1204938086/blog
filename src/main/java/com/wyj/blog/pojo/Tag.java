package com.wyj.blog.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("tags")
public class Tag {
    private String tag;
}
