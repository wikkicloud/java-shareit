package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.util.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * // TODO .
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ItemDto extends BaseEntity {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private User owner;
    private ItemRequest request;

    public static class User extends BaseEntity {

    }

    public static class ItemRequest extends BaseEntity {

    }
}
