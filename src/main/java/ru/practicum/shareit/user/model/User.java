package ru.practicum.shareit.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.util.BaseEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity {
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
}
