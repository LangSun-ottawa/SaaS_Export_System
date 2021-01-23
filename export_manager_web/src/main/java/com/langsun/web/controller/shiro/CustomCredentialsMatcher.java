package com.langsun.web.controller.shiro;


import com.langsun.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author slang
 * @date 2020-08-06 0:06
 * @Param $
 * @return $
 **/
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        System.out.println("密码比较器方法执行");
//        request.setAttribute("error","密码错误");
        //前端页面用户输入的password
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());

        //数据库中查到的password
        //构建安全数据的时候 需要传入第二个参数
        String dbPassword = (String) info.getCredentials();

        //密码加密
//        String s = new Md5Hash(password, username,2).toString();
        String md5Password = Encrypt.md5(password, username);

        return md5Password.equals(dbPassword);
    }
}
