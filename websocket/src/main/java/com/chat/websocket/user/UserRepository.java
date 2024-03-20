package com.chat.websocket.user;

import com.chat.websocket.user.Status;
import com.chat.websocket.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findAllByStatus(Status status);
}
