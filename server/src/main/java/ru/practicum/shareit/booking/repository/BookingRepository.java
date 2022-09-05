package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    //All Bookings
    List<Booking> findByBooker_IdOrderByIdDesc(Long id, Pageable pageable);

    //Bookings owner item
    List<Booking> findByItem_Owner_IdOrderByIdDesc(Long id, Pageable pageable);

    //LastBooking item
    Booking findFirstByItem_IdAndEndBeforeOrderByEndDesc(Long itemId, LocalDateTime end);

    //NextBooking item
    Booking findFirstByItem_IdAndStartAfterOrderByStartAsc(Long itemId, LocalDateTime start);

    //Current Bookings by booker
    List<Booking> findByBooker_IdAndStartBeforeAndEndAfter(
            Long bookerId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    //Current Bookings by owner
    List<Booking> findByItem_Owner_IdAndStartBeforeAndEndAfter(
            Long bookerId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    //Past Bookings by booker
    List<Booking> findByBooker_IdAndEndBefore(Long bookerId, LocalDateTime end, Pageable pageable);

    //Past Bookings by owner
    List<Booking> findByItem_Owner_IdAndEndBefore(Long ownerId, LocalDateTime end, Pageable pageable);

    //Future Bookings by booker
    List<Booking> findByBooker_IdAndStartAfterOrderByStartDesc(Long bookerId, LocalDateTime start,Pageable pageable);

    //Future Bookings by owner
    List<Booking> findByItem_Owner_IdAndStartAfterOrderByStartDesc(Long bookerId, LocalDateTime start,
                                                                   Pageable pageable);

    //Bookings by booker and status
    List<Booking> findByBooker_IdAndStatus(Long bookerId, BookingStatus status, Pageable pageable);

    //Bookings by owner and status
    List<Booking> findByItem_Owner_IdAndStatus(Long bookerId, BookingStatus status, Pageable pageable);
}
