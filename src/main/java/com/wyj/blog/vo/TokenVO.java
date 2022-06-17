package com.wyj.blog.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
//@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVO {
    private Boolean success;
    private TokenCode code;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code.getCode();
    }

    public void setCode(TokenCode code) {
        this.code = code;
    }

    public enum TokenCode{
        SUCCESS(200),
        ERROR(511),
        TIMEOUT(512);
        private final Integer code;
        TokenCode(Integer code){
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
