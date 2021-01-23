package com.langsun.service.chat.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.chat.ChatDao;
import com.langsun.domain.chat.Chat;
import com.langsun.service.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ChatImpl implements ChatService {
    @Autowired
    private ChatDao chatDao;
    @Override
    public PageInfo findAll(int page, String companyId) {
        List<Chat> list4 = chatDao.findAll(companyId);
        System.out.println(list4+"查询的对象");
        PageHelper.startPage( page , 100);
        //2.查询数据
        List<Chat> list = chatDao.findAll(companyId);
        Collections.reverse(list);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        for (Chat chat : list) {
            String format = format1.format(chat.getChatData().getTime());
            chat.setDate(format1.format(chat.getChatData().getTime()));
        }
        //3.返回pageInfo对象
        return new PageInfo(list);
    }

    @Override
    public void toAdd(Chat chat) {
        //加入Id
        Date date = new Date();
        chat.setChatData(date);
        chatDao.toAdd(chat);
    }

    @Override
    public void del(String Id) {
        //通过Id删除
    }
}
