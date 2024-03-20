package com.chat.websocket.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRepository;

    public Optional<String> getChatRoomId(String senderId, String recipientId, boolean createNewChatIfNotExist){
        return chatRepository.findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId).or(
                        () -> {
                            if (createNewChatIfNotExist){
                                var chatId = createChatId(senderId, recipientId);
                                return Optional.of(chatId);
                            }
                            return Optional.empty();
                        }
                );
    }

    private String createChatId(String senderId, String recipientId){

        var chatId = String.format("%s_%s", senderId, recipientId);

        ChatRoom senderRecipient = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId).build();

        ChatRoom recipientSender = ChatRoom.builder()
                .id(chatId)
                .senderId(recipientId)
                .recipientId(senderId).build();

        chatRepository.save(senderRecipient);
        chatRepository.save(recipientSender);
        return chatId;
    }
}
