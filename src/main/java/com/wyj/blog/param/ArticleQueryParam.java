package com.wyj.blog.param;

import lombok.Data;

@Data
public class ArticleQueryParam {
    private String titleOrContent;
    private String type;
    private String tag;
}
