package ru.practicum.mainService.event.service;

import ru.practicum.mainService.event.dto.EventFullDto;
import ru.practicum.mainService.event.dto.EventShortDto;
import ru.practicum.mainService.event.dto.NewEventDto;
import ru.practicum.mainService.event.dto.UpdateEventDto;
import ru.practicum.mainService.event.model.EventSort;
import ru.practicum.mainService.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventFullDto create(NewEventDto newEventDto, Long userId);

    List<EventShortDto> getAllByInitiatorId(Long userId, Integer from, Integer size);

    EventFullDto updateByInitiator(UpdateEventDto updatedEvent, Long eventId, Long userId);

    EventFullDto getEventByIdAndInitiatorId(Long eventId, Long userId);

    EventFullDto updateByAdmin(UpdateEventDto updatedEvent, Long eventId);

    List<EventFullDto> getAllEventsByAdmin(List<Long> users, List<EventState> states, List<Long> categories, LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto getEventByPublic(Long eventId, String uri, String ip);

    List<EventShortDto> getAllEventsByPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                             LocalDateTime rangeEnd, Boolean onlyAvailable, EventSort sort, Integer from,
                                             Integer size, String uri, String ip);

}
