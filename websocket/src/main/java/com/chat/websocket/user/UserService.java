package com.chat.websocket.user;

import com.chat.websocket.user.UserRepository;
import com.chat.websocket.user.Status;
import com.chat.websocket.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    //TODO controlar las excepciones y manejar con optional en vez de var.
    public void saveUser(User user){
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
    }

    public void disconnect(User user){
        var storedUser = userRepository.findById(user.getNickName())
                .orElse(null);
        if (storedUser != null){
            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }

    }

    public List<User> findConnectedUsers(){
        return userRepository.findAll();
        //reactivar solo esta return userRepository.findAllByStatus(Status.ONLINE);
    }
}
