package com.wyj.blog.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("types")
public class Type {
    private String type;
}
