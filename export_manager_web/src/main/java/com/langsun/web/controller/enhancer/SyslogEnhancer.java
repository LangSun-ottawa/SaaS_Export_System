package com.langsun.web.controller.enhancer;

import com.langsun.domain.log.SysLog;
import com.langsun.domain.system.User;
import com.langsun.service.log.SyslogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author slang
 * @date 2020-08-05 16:58
 * @Param $
 * @return $
 **/
@Component
@Aspect
public class SyslogEnhancer {

    @Autowired
    private SyslogService syslogService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    @Around("execution(* com.langsun.web.controller.*.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object obj = new Object();
        try {

            SysLog sysLog = new SysLog();
            sysLog.setIp(request.getRemoteAddr());
            sysLog.setTime(new Date());

            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser != null) {
                sysLog.setUserName(loginUser.getUserName());
                sysLog.setCompanyId(loginUser.getCompanyId());
                sysLog.setCompanyName(loginUser.getCompanyName());
            }

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            sysLog.setMethod(joinPoint.getSignature().getName());

            boolean annotationFlag = method.isAnnotationPresent(RequestMapping.class);
            if (annotationFlag) {
                RequestMapping requestMappingAnno = method.getAnnotation(RequestMapping.class);
                sysLog.setAction(requestMappingAnno.name());
            }

            if (!joinPoint.getSignature().getName().equals("before")) {
                syslogService.save(sysLog);
            }

            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            System.out.println("记录日志出现异常");
            throwable.printStackTrace();
        }

        return obj;
    }

}
