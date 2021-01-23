package com.langsun.dao.chat;

import com.langsun.domain.chat.Chat;

import java.util.List;

public interface ChatDao {
    List<Chat> findAll(String companyId);

    void toAdd(Chat chat);

    void del(String id);
}
