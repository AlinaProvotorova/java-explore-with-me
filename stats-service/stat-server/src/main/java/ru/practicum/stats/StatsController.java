package ru.practicum.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.stats.utils.Constants.DATE_PATTERN;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class StatsController {

    private final StatsService service;

    @PostMapping("/hit")
    public ResponseEntity<EndpointHitDto> createHit(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        log.info("Получен POST запрос по эндпоинту /hit на создание ногового EndpointHitDto {}.", endpointHitDto);
        return new ResponseEntity<>(service.createHit(endpointHitDto), HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ViewStatsDto>> getAllStats(
            @RequestParam("start") @DateTimeFormat(pattern = DATE_PATTERN) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(pattern = DATE_PATTERN) LocalDateTime end,
            @RequestParam(value = "uris", required = false) List<String> uris,
            @RequestParam(value = "unique", defaultValue = "false", required = false) boolean unique) {
        log.info("Получен GET запрос по эндпоинту /stats на получение статистики по посещениям.");
        return new ResponseEntity<>(service.getAllStats(start, end, uris, unique), HttpStatus.OK);
    }

}