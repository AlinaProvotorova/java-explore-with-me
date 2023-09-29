package ru.practicum.mainService.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainService.comment.dto.ResponseCommentDto;
import ru.practicum.mainService.comment.service.CommentService;

@RestController
@Slf4j
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<ResponseCommentDto> getCommentByIdForAdmin(@PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.getByIdByAdmin(commentId), HttpStatus.OK);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteCommentByIdForAdmin(@PathVariable Long commentId) {
        commentService.deleteByIdByAdmin(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
