package com.langsun.web.controller.chat;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.chat.Chat;
import com.langsun.service.chat.ChatService;
import com.langsun.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/chat")
public class ChatController extends BaseController {
    @Autowired
    private ChatService chatService;
    @RequestMapping("/findAll.do")
    public String method(@RequestParam(defaultValue = "0")int page){
        request.setAttribute("loginUsername",loginUser.getUserName());
        request.setAttribute("loginUserId",loginUser.getId());
        session.setAttribute("loginUserId",loginUser.getId());
        System.out.println(loginUser.getUserName()+" : username");
        session.setAttribute("loginUsername",loginUser.getUserName());
        session.setAttribute("loginUserCompanyId",loginUser.getCompanyId());
//        PageInfo pageInfo = chatService.findAll(page, loginUser.getCompanyId());
//        request.setAttribute("page" , pageInfo);
        return "chat/char";
    }

    @RequestMapping("/history.do")
    public String method2(@RequestParam(defaultValue = "0")int page){
        request.setAttribute("loginUsername",loginUser.getUserName());
        request.setAttribute("loginUserId",loginUser.getId());
        PageInfo pageInfo = chatService.findAll(page, loginUser.getCompanyId());
        request.setAttribute("page" , pageInfo);
        return "chat/charhistory";
    }
    @RequestMapping("/toAdd.do")
    public String method1(@RequestParam(defaultValue = "0")int page, Chat chat){
        chat.setCompanyId(companyId);
        chat.setUserId(loginUser.getId());
        chat.setUserName(loginUser.getUserName());
        chatService.toAdd(chat);
        return "redirect:/chat/findAll.do";
    }
}

