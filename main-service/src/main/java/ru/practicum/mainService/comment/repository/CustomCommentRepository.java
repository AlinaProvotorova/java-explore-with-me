package ru.practicum.mainService.comment.repository;


import ru.practicum.mainService.comment.Comment;

import java.util.List;

public interface CustomCommentRepository {

    List<Comment> findAllCommentsForEvent(Long eventId, String keyword, Integer from, Integer size);

}