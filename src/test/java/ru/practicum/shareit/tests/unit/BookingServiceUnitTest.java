package ru.practicum.shareit.tests.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BookingServiceUnitTest {
    private BookingService bookingService;
    @Mock
    BookingRepository bookingRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ItemRepository itemRepository;

    private final User ownerUser = new User(1L, "Name", "test@test.ru");
    private final User someUser = new User(2L, "Name 2", "test2@test.ru");
    private final Item item = new Item(1L, "Клей", "Секундный клей момент", true, ownerUser,
            null);
    private final Booking booking = new Booking(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2),
            item, someUser, BookingStatus.WAITING);

    @BeforeEach
    public void beforeEach() {
        bookingService = new BookingServiceImpl(bookingRepository, userRepository, itemRepository);
    }

    @Test
    public void shouldValidateExceptionItemIsNotAvailable() {
        item.setAvailable(false);
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(someUser));
        Mockito
                .when(itemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(item));
        Exception thrown = assertThrows(ValidateException.class, () -> bookingService.create(booking));
        assertEquals("Item is not available", thrown.getMessage());
    }

    @Test
    public void shouldValidateExceptionBookerIsOwnerItem() {
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(ownerUser));
        Mockito
                .when(itemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(item));
        booking.setBooker(ownerUser);
        Exception thrown = assertThrows(NoSuchElementException.class, () -> bookingService.create(booking));
        assertEquals("User is owner item", thrown.getMessage());
    }

    @Test
    public void shouldValidateExceptionStartInPast() {
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(someUser));
        Mockito
                .when(itemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(item));
        booking.setStart(LocalDateTime.now().minusDays(1));
        Exception thrown = assertThrows(ValidateException.class, () -> bookingService.create(booking));
        assertEquals("Start in past", thrown.getMessage());
    }

    @Test
    public void shouldValidateExceptionEndBeforeStart() {
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(someUser));
        Mockito
                .when(itemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(item));
        booking.setStart(LocalDateTime.now().plusDays(2));
        booking.setEnd(LocalDateTime.now().plusDays(1));
        Exception thrown = assertThrows(ValidateException.class, () -> bookingService.create(booking));
        assertEquals("End before start", thrown.getMessage());
    }

    @Test
    public void shouldExceptionBookingNotFound() {
        Mockito
                .when(bookingRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());
        Exception thrown = assertThrows(NoSuchElementException.class, () ->
                bookingService.approveBooking(1, 2, true));
        assertEquals("Booking not found", thrown.getMessage());
    }

    @Test
    public void shouldExceptionUserNotFound() {
        Mockito
                .when(bookingRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(booking));
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());
        Exception thrown = assertThrows(NoSuchElementException.class, () ->
                bookingService.approveBooking(1, 2, true));
        assertEquals("User not found", thrown.getMessage());
    }

    @Test
    public void shouldExceptionApprovedIsNull() {
        Mockito
                .when(bookingRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(booking));
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(ownerUser));
        Exception thrown = assertThrows(NoSuchElementException.class, () ->
                bookingService.approveBooking(1, 2, null));
        assertEquals("approved is null", thrown.getMessage());
    }

    @Test
    public void shouldExceptionUserNotOwnerItem() {
        Mockito
                .when(bookingRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(booking));
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(someUser));
        Exception thrown = assertThrows(NoSuchElementException.class, () ->
                bookingService.approveBooking(2, 2, true));
        assertEquals("User not owner item", thrown.getMessage());
    }

    @Test
    public void shouldExceptionBookingStatusIsApprovedTrue() {
        Booking bookingApproved = new Booking(1L, LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2), item, someUser, BookingStatus.APPROVED);
        Mockito
                .when(bookingRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(bookingApproved));
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(someUser));
        Exception thrown = assertThrows(ValidateException.class, () ->
                bookingService.approveBooking(1, 2, true));
        assertEquals("Booking status is already approved", thrown.getMessage());
    }

    @Test
    public void shouldExceptionBookingStatusIsApprovedFalse() {
        Booking bookingRejected = new Booking(1L, LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2), item, someUser, BookingStatus.REJECTED);
        Mockito
                .when(bookingRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(bookingRejected));
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(someUser));
        Exception thrown = assertThrows(ValidateException.class, () ->
                bookingService.approveBooking(1, 2, false));
        assertEquals("Booking status is already rejected", thrown.getMessage());
    }
}