package ru.practicum.shareit.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.util.BaseEntity;


/**
 * // TODO .
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends BaseEntity {
    private String name;
    private String email;
}
