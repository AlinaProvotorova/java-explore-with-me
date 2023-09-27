package ru.practicum.mainService.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.practicum.mainService.category.dto.CategoryDto;
import ru.practicum.mainService.user.dto.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.mainService.utils.Constants.DATE_PATTERN;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {

    protected String annotation;
    protected CategoryDto category;
    protected Long confirmedRequests;

    @JsonFormat(pattern = DATE_PATTERN)
    protected LocalDateTime eventDate;

    protected Long id;
    protected UserShortDto initiator;
    protected Boolean paid;
    protected String title;
    protected Long views;

}
