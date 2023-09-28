package ru.practicum.mainService.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RequestCommentDto {

    @NotBlank(message = "Сообщение не может быть пустым или нулевым.")
    @Size(min = 2, max = 1024, message = "Сообщение должно быть от 20 до 1024 символов.")
    private String message;

}