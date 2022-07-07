package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.util.BaseEntity;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookingDto extends BaseEntity {
    private LocalDate start;
    private LocalDate end;
    private Item item;
    private User booker;
    private BookingStatus status;

    public static class Item extends BaseEntity {

    }

    public static class User extends BaseEntity {

    }
}
