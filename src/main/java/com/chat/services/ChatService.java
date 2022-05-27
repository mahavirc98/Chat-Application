package com.chat.services;

import com.chat.payloads.ChatDto;
import com.chat.payloads.ChatResponse;

public interface ChatService {
    ChatDto createChat(ChatDto chatDto, String user);
    void deleteChat(Integer chatId);
    void deleteChatByUser(String user);
    ChatResponse getAllChatByUser(Integer pageNumber, Integer pageSize, String user);
}
