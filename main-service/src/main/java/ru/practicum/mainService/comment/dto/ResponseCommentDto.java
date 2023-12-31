package ru.practicum.mainService.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static ru.practicum.mainService.utils.Constants.DATE_PATTERN;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCommentDto {

    private Long id;
    private Long authorId;
    private Long eventId;

    @JsonFormat(pattern = DATE_PATTERN)
    private LocalDateTime created;
    private String message;

}
