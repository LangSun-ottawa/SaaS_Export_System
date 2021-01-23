package com.langsun.web.controller.shiro;

import com.langsun.domain.system.Module;
import com.langsun.domain.system.User;
import com.langsun.service.system.ModuleService;
import com.langsun.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author slang
 * @date 2020-08-06 0:06
 * @Param $
 * @return $
 **/
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private ModuleService moduleService;

    /**
     * 授权方法的执行时机 取决于xml的配置
     * 如果配置了需要某些权限才可以访问 才执行授权   /system/module/list.do = perms["模块管理"]
     * 当执行的地址栏有访问这样的路径时候才执行匹配
     * <p>
     * 方法名称:获得授权信息 , 用于判断是否具备某些权限
     * 参数
     *
     * @param principalCollection 安全 数据
     * @return AuthorizationInfo 权限的信息
     * 根据登录的信息查询权限的信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权方法执行");
        User user = (User) principalCollection.getPrimaryPrincipal();
        System.out.println(user);
        List<Module> moduleList = moduleService.findByUser(user);

        Set<String> set = new HashSet<>();
        for (Module module : moduleList) {
            set.add(module.getName());
        }
        System.out.println("当前用户具有的权限列表"+set);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(set);
        return info;
    }


    @Autowired
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证方法执行");

        //1.获得用户在页面输入用户名和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());

        //2.查询数据库得到User对象
        User dbUser = userService.findByEmail(username);//有可能密码不正确
        System.out.println(dbUser);
        String dbPassword = dbUser.getPassword();


        //3.最后需要认证信息
        //SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        /**
         *  public SimpleAuthenticationInfo(Object principal, Object credentials, String realmName)
         *  参数1: 安全数据 用户对象 user(必须查询数据库得到)
         *  参数2: 密码( 数据库中用户的密码 )
         *  参数3: realmName 域的名称 随意 只要唯一即可  this.getName() 当前类名
         */
        //参数2:其实可以不传 但是 传入的原因是以后方便获得密码数据
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(dbUser, dbPassword, this.getName());
        //当info对象已经构建完成的时候 自动执行密码比较器的方法 如果没有构建完成
        //构造数据

        return info; //如果没有查询到user对象 直接抛出异常
    }

}
