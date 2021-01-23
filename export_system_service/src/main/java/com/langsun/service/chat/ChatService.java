package com.langsun.service.chat;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.chat.Chat;

public interface ChatService {
    PageInfo findAll(int page, String companyId);

    void toAdd(Chat chat);

    void del(String Id);
}
