package ru.practicum.mainService.request;


import ru.practicum.mainService.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.mainService.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.mainService.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    ParticipationRequestDto create(Long userId, Long eventId);

    List<ParticipationRequestDto> getRequestsByUserId(Long userId);

    ParticipationRequestDto cancel(Long userId, Long requestId);

    EventRequestStatusUpdateResultDto processRequestsByInitiator(EventRequestStatusUpdateRequestDto updateRequest,
                                                                 Long userId,
                                                                 Long eventId);

    List<ParticipationRequestDto> getRequestsByInitiator(Long userId, Long eventId);

}