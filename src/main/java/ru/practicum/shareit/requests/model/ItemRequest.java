package ru.practicum.shareit.requests.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.util.BaseEntity;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;

/**
 * // TODO .
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ItemRequest extends BaseEntity {
    private String description;
    private User requestor;
    private LocalDate created;
}
