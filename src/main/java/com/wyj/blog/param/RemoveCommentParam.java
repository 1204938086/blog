package com.wyj.blog.param;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class RemoveCommentParam {
    private ObjectId articleId;
    private ObjectId commentId;
}
