package ru.practicum.mainService.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainService.comment.dto.RequestCommentDto;
import ru.practicum.mainService.comment.dto.ResponseCommentDto;
import ru.practicum.mainService.comment.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@Slf4j
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
public class PrivateCommentController {

    private final CommentService commentService;

    @PostMapping("/{eventId}")
    public ResponseEntity<ResponseCommentDto> createComment(@RequestBody @Valid RequestCommentDto newComment,
                                                            @PathVariable Long userId, @PathVariable Long eventId) {
        return new ResponseEntity<>(commentService.create(newComment, userId, eventId), HttpStatus.CREATED);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ResponseCommentDto> updateComment(@RequestBody @Valid RequestCommentDto newComment,
                                                            @PathVariable Long userId, @PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.update(newComment, userId, commentId), HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<ResponseCommentDto> getCommentByIdForUser(@PathVariable Long userId,
                                                                    @PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.getByIdByUser(userId, commentId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ResponseCommentDto>> getListCommentsForUsers(@PathVariable Long userId) {
        return new ResponseEntity<>(commentService.getUsersComments(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteCommentByIdForUser(@PathVariable Long userId, @PathVariable Long commentId) {
        commentService.deleteByIdByUser(userId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}