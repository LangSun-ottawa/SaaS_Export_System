package com.langsun.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.langsun.utils.MailUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;

/**
 * @author slang
 * @date 2020-09-03 13:24
 * @Param $
 * @return $
 **/
public class MyListener implements MessageListener {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onMessage(Message message) {

        try {
            System.out.println(message.toString());
            JsonNode jsonNode = MAPPER.readTree(message.getBody());
            String to = jsonNode.get("to").asText().replaceAll("\"", "");
            String subject = jsonNode.get("subject").asText().replaceAll("\"", "");
            String content = jsonNode.get("content").asText().replaceAll("\"", "");
            MailUtil.sendMsg(to, subject, content);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
