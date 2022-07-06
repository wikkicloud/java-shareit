package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

public class BookingMapper {
    public static BookingDto toBookingDto (Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());

        BookingDto.Item item = new BookingDto.Item();
        item.setId(booking.getItem().getId());
        bookingDto.setItem(item);

        BookingDto.User user = new BookingDto.User();
        user.setId(booking.getBooker().getId());
        bookingDto.setBooker(user);

        bookingDto.setStatus(booking.getStatus());

        return bookingDto;
    }
}
