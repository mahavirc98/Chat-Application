package com.chat.controller;

import com.chat.config.AppConstants;
import com.chat.payloads.ApiResponse;
import com.chat.payloads.ChatDto;
import com.chat.payloads.ChatResponse;
import com.chat.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatlogs/")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/{user}")
    public ResponseEntity<ChatDto> createChat(@RequestBody ChatDto chatDto, @PathVariable String user){
        ChatDto createChat =this.chatService.createChat(chatDto,user);

        return new ResponseEntity<ChatDto>(createChat, HttpStatus.CREATED);
    }
    @GetMapping("/{user}")
    public ResponseEntity<ChatResponse>getAllChatByUser(
            @PathVariable String user,
            @RequestParam(value = "start", defaultValue = AppConstants.start, required = false) Integer start,
            @RequestParam(value = "limit", defaultValue = AppConstants.limit, required = false) Integer limit){
            ChatResponse chatResponse =this.chatService.getAllChatByUser(start,limit,user);
            return new ResponseEntity<ChatResponse>(chatResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{user}/{chatId}")
    public ApiResponse deletePost(@PathVariable String user, @PathVariable Integer chatId) {
        this.chatService.deleteChat(chatId);
        return new ApiResponse("Chat is successfully deleted !!", true);
    }

    @DeleteMapping("/{user}")
    public ApiResponse deletePost(@PathVariable String user) {
        this.chatService.deleteChatByUser(user);
        return new ApiResponse("User chat is successfully deleted !!", true);
    }
}
