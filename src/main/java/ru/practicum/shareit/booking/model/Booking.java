package ru.practicum.shareit.booking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.util.BaseEntity;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class Booking extends BaseEntity {
    private LocalDate start;
    private LocalDate end;
    private Item item;
    private User booker;
    private BookingStatus status;
}
