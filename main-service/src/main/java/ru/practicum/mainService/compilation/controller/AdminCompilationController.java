package ru.practicum.mainService.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainService.compilation.dto.CompilationRequestDto;
import ru.practicum.mainService.compilation.dto.CompilationResponseDto;
import ru.practicum.mainService.compilation.service.CompilationService;
import ru.practicum.mainService.utils.Marker.OnCreate;
import ru.practicum.mainService.utils.Marker.OnUpdate;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    public ResponseEntity<CompilationResponseDto> create(
            @RequestBody @Validated(OnCreate.class) CompilationRequestDto compilationRequestDto) {
        return new ResponseEntity<>(compilationService.create(compilationRequestDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationResponseDto> update(
            @RequestBody @Validated(OnUpdate.class) CompilationRequestDto compilationRequestDto,
            @PathVariable Long compId) {
        return new ResponseEntity<>(compilationService.update(compilationRequestDto, compId), HttpStatus.OK);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<?> delete(@PathVariable Long compId) {
        compilationService.delete(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
