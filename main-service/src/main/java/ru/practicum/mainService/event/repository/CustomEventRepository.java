package ru.practicum.mainService.event.repository;


import ru.practicum.mainService.event.model.Event;
import ru.practicum.mainService.event.model.EventSort;
import ru.practicum.mainService.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomEventRepository {

    List<Event> findEventsByAdmin(List<Long> users, List<EventState> states, List<Long> categories,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    List<Event> findEventsByPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd, Integer from, Integer size, EventSort sort);

}
