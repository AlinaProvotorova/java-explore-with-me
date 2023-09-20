package ru.practicum.mainService.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainService.event.dto.EventFullDto;
import ru.practicum.mainService.event.dto.EventShortDto;
import ru.practicum.mainService.event.dto.NewEventDto;
import ru.practicum.mainService.event.dto.UpdateEventDto;
import ru.practicum.mainService.event.service.EventService;
import ru.practicum.mainService.request.RequestService;
import ru.practicum.mainService.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.mainService.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.mainService.request.dto.ParticipationRequestDto;
import ru.practicum.mainService.utils.Marker.OnCreate;
import ru.practicum.mainService.utils.Marker.OnUpdate;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {

    private final EventService eventService;
    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<EventFullDto> create(@Validated(OnCreate.class) @RequestBody NewEventDto newEventDto,
                                               @PathVariable Long userId) {
        return new ResponseEntity<>(eventService.create(newEventDto, userId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getAllByInitiatorId(@PathVariable Long userId,
                                                                   @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
                                                                   @RequestParam(defaultValue = "10", required = false) @Positive Integer size) {
        return new ResponseEntity<>(eventService.getAllByInitiatorId(userId, from, size), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEventByIdAndInitiatorId(@PathVariable Long eventId,
                                                                   @PathVariable Long userId) {
        ;
        return new ResponseEntity<>(eventService.getEventByIdAndInitiatorId(eventId, userId), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateByInitiator(
            @Validated(OnUpdate.class) @RequestBody UpdateEventDto updatedEvent,
            @PathVariable Long eventId,
            @PathVariable Long userId) {
        return new ResponseEntity<>(eventService.updateByInitiator(updatedEvent, eventId, userId), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResultDto> processRequestsByInitiator(
            @RequestBody @Valid EventRequestStatusUpdateRequestDto updateRequest,
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        return new ResponseEntity<>(requestService.processRequestsByInitiator(updateRequest, userId, eventId),
                HttpStatus.OK);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getRequestsByInitiator(@PathVariable Long userId,
                                                                                @PathVariable Long eventId) {
        return new ResponseEntity<>(requestService.getRequestsByInitiator(userId, eventId), HttpStatus.OK);
    }

}
