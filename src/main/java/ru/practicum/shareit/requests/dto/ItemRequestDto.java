package ru.practicum.shareit.requests.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ItemRequestDto {
    private Long id;
    private String description;
    private User requestor;
    private LocalDate created;

    @Data
    public static class User {
        private Long id;
    }
}
