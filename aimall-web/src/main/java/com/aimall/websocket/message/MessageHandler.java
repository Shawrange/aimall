package com.aimall.websocket.message;


import com.aimall.entity.dto.MessageSendDTO;
import org.springframework.stereotype.Component;

@Component("messageHandler")
public interface MessageHandler {

    void listenMessage();

    void sendMessage(MessageSendDTO sendDto);
}

