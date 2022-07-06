package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.util.BaseEntity;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * // TODO .
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class Item extends BaseEntity {
    @NotBlank
    private String name;
    private String description;
    private boolean available;
    @NotNull
    private User owner;
    private ItemRequest request;
}
