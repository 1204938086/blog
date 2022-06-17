package com.wyj.blog.vo;


public enum BaseVoCode {
    SUCCESS(200),
    REMOVE_ARTICLE_ERROR(521),
    CREATE_TAG_ERROR(531),
    TAG_EXISTS(532),
    REMOVE_TAG_ERROR(533),
    CREATE_TYPE_ERROR(541),
    TYPE_EXISTS(542),
    REMOVE_TYPE_ERROR(543),
    MODIFY_ARTICLE_ERROR(551),
    CREATE_COMMENT_ERR(561),
    REMOVE_COMMENT_ERR(562);

    private final Integer code;
    BaseVoCode(Integer code){
        this.code = code;
    }
    public Integer getCode(){
        return this.code;
    }
}
