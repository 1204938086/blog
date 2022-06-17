package com.wyj.blog.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleVO {
    public enum CreateArticleCode{
        SUCCESS(200),
        ERROR(500);
        private final Integer code;
        CreateArticleCode(Integer code){
            this.code = code;
        }
        public Integer getCode(){
            return this.code;
        }
        @Override
        public String toString() {
            return ""+code;
        }
    }

    private Boolean success;
    private CreateArticleCode code;
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public CreateArticleVO setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public Integer getCode() {
        return code.getCode();
    }

    public CreateArticleVO setCode(CreateArticleCode code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CreateArticleVO setMessage(String message) {
        this.message = message;
        return this;
    }
}
