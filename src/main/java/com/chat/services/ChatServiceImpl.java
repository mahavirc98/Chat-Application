package com.chat.services;

import com.chat.entities.Chat;
import com.chat.exceptions.ResourceNotFoundException;
import com.chat.payloads.ChatDto;
import com.chat.payloads.ChatResponse;
import com.chat.repositories.ChatRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepo chatRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ChatDto createChat(ChatDto chatDto, String user) {
        Chat chat = this.modelMapper.map(chatDto,Chat.class);
        chat.setUser(user);
        Chat addedChat =this.chatRepo.save(chat);
        return this.modelMapper.map(addedChat,ChatDto.class);
    }

    @Override
    public void deleteChat(Integer chatId) {
        Chat chat = this.chatRepo.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat ", "chat id", chatId));
        this.chatRepo.delete(chat);
    }

    @Override
    public void deleteChatByUser(String user) {
        this.chatRepo.deleteByUser(user);

    }

    @Override
    public ChatResponse getAllChatByUser(Integer pageNumber, Integer pageSize, String user) {
        String sortBy = "id";
        Sort sort = Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(pageNumber,pageSize,sort);

        Page<Chat> pageChat = this.chatRepo.findByUser(user,p);
        List<Chat> chats =pageChat.getContent();
        List<ChatDto> chatDtos = chats.stream().map((chat)->this.modelMapper.map(chat,ChatDto.class)).collect(Collectors.toList());
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setPageNumber(pageChat.getNumber());
        chatResponse.setPageSize(pageChat.getSize());
        chatResponse.setContent(chatDtos);
        chatResponse.setTotalElements(pageChat.getTotalElements());
        chatResponse.setTotalPages(pageChat.getTotalPages());
        chatResponse.setLastPage(pageChat.isLast());
        return chatResponse;

    }
}
