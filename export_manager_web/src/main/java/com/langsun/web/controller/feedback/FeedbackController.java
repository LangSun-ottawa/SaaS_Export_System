package com.langsun.web.controller.feedback;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.langsun.domain.cargo.ContractProduct;
import com.langsun.domain.cargo.ContractProductExample;
import com.langsun.domain.cargo.Factory;
import com.langsun.domain.cargo.FactoryExample;
import com.langsun.domain.feedback.Feedback;
import com.langsun.domain.finance.Finance;
import com.langsun.domain.finance.FinanceExample;
import com.langsun.domain.system.User;
import com.langsun.service.cargo.ContractProductService;
import com.langsun.service.cargo.FactoryService;
import com.langsun.service.company.CompanyService;
import com.langsun.service.feedback.FeedbackService;
import com.langsun.service.finance.FinanceService;
import com.langsun.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author slang
 * @date 2020-09-10 1:51
 * @Param $
 * @return $
 **/
@Controller
@RequestMapping("/feedback")
public class FeedbackController extends BaseController {

    @Autowired
    private FeedbackService feedbackService;


    @RequestMapping("/personal/view.do")
    public String personalView() {
        return "feedback/userIdea";
    }



    @RequestMapping("/system/view.do")
    public String systemView() {
        return "feedback/systemIdea";
    }


    @RequestMapping("/update1.do")
    public String personalList(String idea) {
//        System.out.println("???????????");
        Feedback feedback = new Feedback();
        feedback.setContent(idea);
        feedback.setUserId(loginUser.getId());
        feedback.setUserName(loginUser.getUserName());
        feedback.setCompanyId(companyId);
        feedback.setRead(0);
        feedback.setState(0);
        System.out.println(feedback);
        feedbackService.save(feedback);

        return "redirect:personal/view.do";
    }

    @RequestMapping("/update2.do")
    public String systemList(String idea) {
        Feedback feedback = new Feedback();
        feedback.setContent(idea);
        feedback.setUserId(loginUser.getId());
        feedback.setUserName(loginUser.getUserName());
        feedback.setCompanyId(companyId);
        feedback.setState(1);
        feedback.setRead(0);
        feedbackService.save(feedback);

        return "redirect:system/view.do";
    }

    @RequestMapping("/unread.do")
    @ResponseBody
    public List<Feedback> unread() {

        Integer degree = loginUser.getDegree();

        List<Feedback> unread = null;

        /*只有SaaS可以观看系统反馈*/
        if (degree == 0) {
            unread = feedbackService.unread(1);

            //0用户/1系统 反馈
            //未读0
            /*只有企业可以观看用户反馈*/
        } else if (degree == 1) {
            unread = feedbackService.unread(0);
        }

        return unread;
    }

    @RequestMapping("/messageChecked.do")
    public String messageChecked(String feedbackId) {
        Feedback feedback = feedbackService.messageChecked(feedbackId);
        request.setAttribute("feedback",feedback);
        return "feedback/message";
    }

    @RequestMapping("/checkHistory.do")
    public String checkHistory() {
        Integer degree = loginUser.getDegree();
        List<Feedback> list = null;
        //0用户/1系统 反馈
        if(degree == 0){
            list =  feedbackService.findAll(1,companyId);
        }else if(degree == 1){
            list = feedbackService.findAll(0,companyId);
        }
        request.setAttribute("user", loginUser);
        request.setAttribute("list", list);
        return "feedback/allMessage";
    }

    /*单个删除*/
    @RequestMapping("/deleteIdea")
    public String deleteIdea(String id) {
        feedbackService.deleteById(id);
        return "redirect:/feedback/checkHistory.do";
    }

    /*多个删除*/
    @RequestMapping("/deleteIdeas")
    public String deleteIdeas(String[] ids) {
        feedbackService.deleteIdeas(ids);
        return "redirect:/feedback/checkHistory.do";
    }

    /*返回主页面*/
    @RequestMapping("/toHome")
    public String toHome() {

        return "home/home";
    }

    @RequestMapping("/toMain")
    public String toMain() {

        return "home/main";
    }

}
