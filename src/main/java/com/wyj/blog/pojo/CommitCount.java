package com.wyj.blog.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("commitCount")
@Data
public class CommitCount {
    private Date date;
    private Integer commit;
}
