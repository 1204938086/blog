package com.wyj.blog.controller;


import com.wyj.blog.config.BlogUserConfig;
import com.wyj.blog.param.CheckTokenParam;
import com.wyj.blog.utils.TokenUtils;
import com.wyj.blog.vo.LoginVO;
import com.wyj.blog.vo.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class UserController {
    @Autowired
    private BlogUserConfig blogUserConfig;

    @PostMapping("/login")
    public LoginVO login(@RequestBody BlogUserConfig blogUser){
        if(blogUserConfig.getUsername().equals(blogUser.getUsername())
                && blogUserConfig.getPassword().equals(blogUser.getPassword()))
        {
            return new LoginVO()
                    .setSuccess(true)
                    .setCode(LoginVO.LoginCode.SUCCESS)
                    .setMessage("登录成功")
                    .setToken(TokenUtils.CreateToken(blogUser.getUsername(),
                            blogUser.getPassword(),blogUserConfig.getTtlMinutes()));
        }else if(!blogUserConfig.getUsername().equals(blogUser.getUsername())){
            return new LoginVO(false,
                    LoginVO.LoginCode.USERNAMEERR,
                    "账号不存在",
                    null);
        }else{
            return new LoginVO(false,
                    LoginVO.LoginCode.PASSWORDERR,
                    "密码错误",
                    null);
        }
    }
    @PostMapping("/verifytoken")
    public TokenVO verifytoken(@RequestBody CheckTokenParam checkTokenParam){
        Integer ret = TokenUtils.VerifyToken(checkTokenParam.getToken(),blogUserConfig.getPassword());
        if (ret == 1){
            return new TokenVO(true, TokenVO.TokenCode.SUCCESS);
        }else if(ret == 0){
            return new TokenVO(false, TokenVO.TokenCode.TIMEOUT);
        }else {
            return new TokenVO(false, TokenVO.TokenCode.ERROR);
        }
    }
}
