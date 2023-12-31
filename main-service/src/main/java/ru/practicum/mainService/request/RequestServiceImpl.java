package ru.practicum.mainService.request;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainService.event.model.Event;
import ru.practicum.mainService.event.model.EventState;
import ru.practicum.mainService.event.repository.EventRepository;
import ru.practicum.mainService.exceptions.CantDoException;
import ru.practicum.mainService.exceptions.NotFoundException;
import ru.practicum.mainService.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.mainService.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.mainService.request.dto.ParticipationRequestDto;
import ru.practicum.mainService.request.dto.RequestMapper;
import ru.practicum.mainService.request.model.Request;
import ru.practicum.mainService.request.model.RequestState;
import ru.practicum.mainService.user.User;
import ru.practicum.mainService.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public ParticipationRequestDto create(Long userId, Long eventId) {
        User user = userExistsAndUserGet(userId);
        Event event = eventExistsAndGet(eventId);
        requestRepeatExist(userId, eventId);
        requestOwnerEventExist(userId, event);
        requestPublishedExist(event);
        participantLimitCheck(1, event);

        Request element = Request.builder()
                .created(LocalDateTime.now())
                .status(RequestState.PENDING)
                .event(event)
                .requester(user)
                .build();

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            element.setStatus(RequestState.CONFIRMED);
        }

        ParticipationRequestDto participationRequestDto = requestMapper
                .toParticipationRequestDto(requestRepository.save(element));

        log.info("Создан новый request с ID {}.", element.getId());
        return participationRequestDto;

    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getRequestsByUserId(Long userId) {
        userExist(userId);
        List<ParticipationRequestDto> requests = requestMapper.toParticipationRequestDto(
                requestRepository.findRequestsByRequesterId(userId));
        log.info("Получен список Requests созданных User с ID {}", userId);
        return requests;
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        userExist(userId);
        Request request = findRequestByUserIdAndRequestId(userId, requestId);
        request.setStatus(RequestState.CANCELED);
        ParticipationRequestDto element = requestMapper.toParticipationRequestDto(requestRepository.save(request));
        log.info("User с ID {} отменил свой Request с ID {}.", userId, requestId);
        return element;
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResultDto processRequestsByInitiator(
            EventRequestStatusUpdateRequestDto updateRequest, Long userId, Long eventId) {

        userExist(userId);
        Event event = eventOwnerExistsAndGet(eventId, userId);
        List<Long> requestIds = updateRequest.getRequestIds();

        if (shouldSkipProcessing(requestIds, event)) {
            return new EventRequestStatusUpdateResultDto(new ArrayList<>(), new ArrayList<>()
            );
        }

        List<Request> requests = requestRepository.findRequestsByIdIn(requestIds);
        if (requests.size() != requestIds.size()) {
            throw new NotFoundException("Не все Requests были найдены");
        }

        pendingCheck(requests);

        List<Request> confirmed = new ArrayList<>();
        List<Request> rejected = new ArrayList<>();

        switch (updateRequest.getStatus()) {
            case REJECTED:
                requests.forEach(r -> r.setStatus(RequestState.REJECTED));
                rejected = requestRepository.saveAll(requests);
                break;
            case CONFIRMED:
                participantLimitCheck(requestIds.size(), event);
                requests.forEach(r -> r.setStatus(RequestState.CONFIRMED));
                confirmed = requestRepository.saveAll(requests);
                break;
        }
        log.info("Обновление Request {} для User с ID {} для Event с ID {}.", updateRequest, userId, eventId);
        return new EventRequestStatusUpdateResultDto(requestMapper.toParticipationRequestDto(confirmed),
                requestMapper.toParticipationRequestDto(rejected));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getRequestsByInitiator(Long userId, Long eventId) {
        List<ParticipationRequestDto> elements = requestMapper.toParticipationRequestDto(
                requestRepository.findRequestsByEventInitiatorIdAndEventId(userId, eventId));
        log.info("Получен список Requests для User (Инициатор) с ID {} для Event с ID {}.", userId, eventId);
        return elements;
    }

    private Request findRequestByUserIdAndRequestId(Long userId, Long requestId) {
        Request request = requestRepository.findRequestsByRequesterIdAndId(userId, requestId)
                .orElseThrow(() -> new NotFoundException(Request.class, requestId));
        log.info("Получен Request с ID {} для User с ID {}.", requestId, userId);
        return request;
    }

    private void participantLimitCheck(Integer requestToAdd, Event event) {
        if (requestRepository.getConfirmedRequests(event.getId()) + requestToAdd > event.getParticipantLimit()
                && event.getParticipantLimit() != 0) {
            throw new CantDoException("Превышение лимита участников.");
        }
    }

    private void pendingCheck(List<Request> requests) {
        if (!requests.stream()
                .map(Request::getStatus)
                .allMatch(s -> s.equals(RequestState.PENDING))) {
            throw new CantDoException("Подтвержденные или отмененные requests не могут быть изменены.");
        }
    }

    private boolean shouldSkipProcessing(List<Long> numbersId, Event event) {
        return numbersId.isEmpty() || event.getParticipantLimit() == 0 || !event.getRequestModeration();
    }

    private Event eventOwnerExistsAndGet(Long eventId, Long userId) {
        return eventRepository.findByIdAndInitiatorIdAndLock(eventId, userId)
                .orElseThrow(() -> new NotFoundException(Event.class, eventId));
    }

    private Event eventExistsAndGet(Long eventId) {
        return eventRepository.findByIdAndLock(eventId)
                .orElseThrow(() -> new NotFoundException(Event.class, eventId));
    }

    private void requestOwnerEventExist(Long userId, Event event) {
        if (userId.equals(event.getInitiator().getId())) {
            throw new CantDoException("Не удается создать request на ваше собственное мероприятие.");
        }
    }

    private void requestPublishedExist(Event event) {
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new CantDoException("Не удается создать request на неопубликованное событие.");
        }
    }

    private void requestRepeatExist(Long userId, Long eventId) {
        if (requestRepository.findRequestByRequesterIdAndEventId(userId, eventId).isPresent()) {
            throw new CantDoException("Невозможно дважды создавать одни и те же запросы на участие.");
        }
    }

    private User userExistsAndUserGet(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(User.class, userId));
    }

    private void userExist(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(User.class, userId);
        }
    }

}