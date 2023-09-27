package ru.practicum.mainService.compilation.dto;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.mainService.compilation.Compilation;
import ru.practicum.mainService.event.dto.EventMapper;
import ru.practicum.mainService.event.dto.EventShortDto;
import ru.practicum.mainService.event.model.Event;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface CompilationMapper {

    @Mapping(target = "events", source = "eventDtos")
    CompilationResponseDto toCompilationDto(Compilation compilation, List<EventShortDto> eventDtos);

    @Mapping(target = "events", source = "events")
    Compilation toCompilation(CompilationRequestDto compilationRequestDto, Set<Event> events);

}
