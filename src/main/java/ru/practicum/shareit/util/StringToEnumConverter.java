package ru.practicum.shareit.util;

import org.springframework.core.convert.converter.Converter;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.exception.UnknownStateException;

public class StringToEnumConverter implements Converter<String, BookingState> {
    @Override
    public BookingState convert(String s) {
        try {
            return BookingState.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownStateException(s.toUpperCase());
        }
    }
}
