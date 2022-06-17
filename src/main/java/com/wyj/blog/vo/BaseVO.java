package com.wyj.blog.vo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BaseVO {
    private Boolean success;
    private String message;
    private BaseVoCode code;

    public Boolean getSuccess() {
        return success;
    }

    public BaseVO setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BaseVO setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getCode() {
        return code.getCode();
    }

    public BaseVO setCode(BaseVoCode code) {
        this.code = code;
        return this;
    }
}
