package ru.practicum.mainService.event.dto;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.mainService.category.Category;
import ru.practicum.mainService.category.dto.CategoryMapper;
import ru.practicum.mainService.event.model.Event;
import ru.practicum.mainService.event.model.EventState;
import ru.practicum.mainService.location.Location;
import ru.practicum.mainService.location.LocationMapper;
import ru.practicum.mainService.user.User;
import ru.practicum.mainService.user.dto.UserMapper;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class,
        CategoryMapper.class,
        LocationMapper.class
})
public interface EventMapper {

    @Mapping(target = "createdOn", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    Event toEvent(NewEventDto newEventDto, User initiator, Category category, Location location, EventState state);

    EventFullDto toEventFullDto(Event event, Long confirmedRequests, Long views);

    EventShortDto toEventShortDto(Event event, Long confirmedRequests, Long views);

}
