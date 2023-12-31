package ru.practicum.mainService.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainService.event.dto.EventFullDto;
import ru.practicum.mainService.event.dto.EventShortDto;
import ru.practicum.mainService.event.model.EventSort;
import ru.practicum.mainService.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.mainService.utils.Constants.DATE_PATTERN;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {

    private final EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<EventFullDto> getEventByPublic(@PathVariable Long id, HttpServletRequest request) {
        return new ResponseEntity<>(eventService.getEventByPublic(id, request.getRequestURI(), request.getRemoteAddr()),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getAllEventsByPublic(@RequestParam(required = false) String text,
                                                                    @RequestParam(required = false) List<Long> categories,
                                                                    @RequestParam(required = false) Boolean paid,
                                                                    @RequestParam(required = false) @DateTimeFormat(pattern = DATE_PATTERN) LocalDateTime rangeStart,
                                                                    @RequestParam(required = false) @DateTimeFormat(pattern = DATE_PATTERN) LocalDateTime rangeEnd,
                                                                    @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
                                                                    @RequestParam(required = false) EventSort sort,
                                                                    @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                                                    @RequestParam(required = false, defaultValue = "10") @Positive Integer size,
                                                                    HttpServletRequest request) {
        return new ResponseEntity<>(
                eventService.getAllEventsByPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                        from, size, request.getRequestURI(), request.getRemoteAddr()), HttpStatus.OK);
    }

}
