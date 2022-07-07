package ru.practicum.shareit.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.util.BaseEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends BaseEntity {
    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;
}
