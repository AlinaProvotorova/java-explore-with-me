package ru.practicum.mainService.compilation.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.mainService.event.dto.EventShortDto;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CompilationResponseDto {

    private Long id;
    private List<EventShortDto> events;
    private Boolean pinned;
    private String title;

}
