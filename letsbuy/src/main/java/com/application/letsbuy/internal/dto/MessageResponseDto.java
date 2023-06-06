package com.application.letsbuy.internal.dto;

import com.application.letsbuy.internal.entities.Chat;
import com.application.letsbuy.internal.entities.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponseDto {

    private Long id;
    private String message;
    private Long idUser;
    private LocalDateTime postedAt;

    public MessageResponseDto(Message message) {
        this.id = message.getId();
        this.message = message.getMessage();
        this.postedAt = message.getPostedAt();
        this.idUser = message.getIdUser();
    }

    public static List<MessageResponseDto> convert(List<Message> messages) {
        return messages.stream().map(MessageResponseDto::new).collect(Collectors.toList());
    }
}
