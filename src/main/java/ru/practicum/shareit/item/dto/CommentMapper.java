package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.user.model.User;

public class CommentMapper {
    public static CommentDto toCommentDto(User author, Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .authorName(author.getName())
                .text(comment.getText())
                .created(comment.getCreated())
                .build();
    }
}
