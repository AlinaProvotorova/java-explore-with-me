package ru.practicum.mainService.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainService.event.dto.EventFullDto;
import ru.practicum.mainService.event.dto.UpdateEventDto;
import ru.practicum.mainService.event.model.EventState;
import ru.practicum.mainService.event.service.EventService;
import ru.practicum.mainService.utils.Marker.OnUpdateAdmin;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.mainService.utils.Constants.DATE_PATTERN;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateByAdmin(
            @RequestBody @Validated(OnUpdateAdmin.class) UpdateEventDto updatedEvent,
            @PathVariable Long eventId) {
        return new ResponseEntity<>(eventService.updateByAdmin(updatedEvent, eventId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getAllEventsByAdmin(@RequestParam(required = false) List<Long> users,
                                                                  @RequestParam(required = false) List<EventState> states,
                                                                  @RequestParam(required = false) List<Long> categories,
                                                                  @RequestParam(required = false) @DateTimeFormat(pattern = DATE_PATTERN) LocalDateTime rangeStart,
                                                                  @RequestParam(required = false) @DateTimeFormat(pattern = DATE_PATTERN) LocalDateTime rangeEnd,
                                                                  @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                                                  @RequestParam(required = false, defaultValue = "10") @Positive Integer size) {
        return new ResponseEntity<>(
                eventService.getAllEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size),
                HttpStatus.OK);
    }

}
