package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    //All Bookings
    List<Booking> findByBooker_IdOrderByIdDesc(Long id);

    //Bookings owner item
    List<Booking> findByItem_Owner_IdOrderByIdDesc(Long id);

    //LastBooking item
    Booking findFirstByItem_IdAndEndBeforeOrderByEndDesc(Long itemId, LocalDateTime end);

    //NextBooking item
    Booking findFirstByItem_IdAndStartAfterOrderByStartAsc(Long itemId, LocalDateTime start);

    //Current Bookings
    List<Booking> findByBooker_IdAndStartAfterAndEndBefore(
            Long bookerId, LocalDateTime start, LocalDateTime end);

    //Past Bookings
    List<Booking> findByBooker_IdAndEndBefore(Long bookerId, LocalDateTime end);

    //Future Bookings
    List<Booking> findByBooker_IdAndStartAfter(Long bookerId, LocalDateTime start);

    //Bookings by status
    List<Booking> findByBooker_IdAndStatus(Long bookerId, BookingStatus status);
}
