package com.chat.websocket.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @MessageMapping("/app/user.addUser")
    @SendTo("/user/{userId}/topic/public")
    //@SendTo("/user/{userId}/topic")
    public User addUser(@Payload User user){
        userService.saveUser(user);
        return user;
    }

    @MessageMapping("/app/user.disconnectUser")
    @SendTo("/user/{userId}/topic/public")
    //@SendTo("/user/public")
    public User disconnect(@Payload User user){
        userService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers(){
        return ResponseEntity.ok(userService.findConnectedUsers());
    }



}
