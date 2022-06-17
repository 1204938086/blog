package com.wyj.blog.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

//@Data
//@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO implements Serializable {
    private Boolean success;
    private LoginCode code;
    private String message;
    private String token;

    public Boolean getSuccess() {
        return success;
    }
    public LoginVO setSuccess(Boolean success) {
        this.success = success;
        return this;
    }
    public Integer getCode() {
        return code.getCode();
    }
    public LoginVO setCode(LoginCode code) {
        this.code = code;
        return this;
    }
    public String getMessage() {
        return message;
    }
    public LoginVO setMessage(String message) {
        this.message = message;
        return this;
    }
    @JSONField()
    public String getToken() {
        return token;
    }

    public LoginVO setToken(String token) {
        this.token = token;
        return this;
    }


    public enum LoginCode{
        SUCCESS(200),
        USERNAMEERR(501),
        PASSWORDERR(502);


        private final Integer code;

        LoginCode(Integer code){
            this.code = code;
        }
        @JSONField(name = "code",serialize = true)
        public Integer getCode(){
            return this.code;
        }

        @Override
        public String toString() {
            return ""+code;
        }
    }
}
