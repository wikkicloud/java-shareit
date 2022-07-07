package ru.practicum.shareit.requests.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.util.BaseEntity;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class ItemRequestDto extends BaseEntity {
    private String description;
    private User requestor;
    private LocalDate created;

    public static class User extends BaseEntity {

    }
}
