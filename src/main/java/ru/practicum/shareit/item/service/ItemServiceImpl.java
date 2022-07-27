package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.ValidateExeption;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public Item create(long userId, Item item) {
        User owner = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        item.setOwner(owner);
        log.info("Create Item {}", item);
        return itemRepository.save(item);
    }

    @Override
    public Item update(long userId, long itemId, Item item) {
        Item updatedItem = getValidItemDto(userId, itemId, item);
        updatedItem.setId(itemId);
        log.info("Update Item {}", updatedItem);
        return itemRepository.save(updatedItem);
    }

    @Override
    public Item getById(long id) {
        return itemRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Item not found"));
    }

    @Override
    public List<Item> searchByText(String text) {
        if (text != null && !text.isBlank())
            return itemRepository.findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCaseAndAvailableTrue(text,
                    text);
        //Return empty List
        return new ArrayList<>();
    }

    @Override
    public Comment addCommentToItem(Comment comment) {
        //Check comment
        if (comment.getText() == null || comment.getText().isBlank())
            throw new ValidateExeption("Text empty");
        //check booking item
        List<Booking> bookingList = bookingRepository.findByBooker_IdAndEndBefore(comment.getAuthor().getId(),
                LocalDateTime.now());
        if (bookingList.isEmpty())
            throw new ValidateExeption("User not booking its item");
        comment.setCreated(LocalDateTime.now());
        log.info("Add comment {}", comment);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findCommentsByItemId(long itemId) {
        return commentRepository.findByItem_IdOrderByCreatedDesc(itemId);
    }

    @Override
    public List<Item> getAllByUser(long userId) {
        return itemRepository.findByOwner_Id(userId);
    }

    private Item getValidItemDto(long userId, long itemId, Item item) {
        Item updatedItem = itemRepository.findById(itemId).orElseThrow(
                () -> new NoSuchElementException("Item not found"));
        // Check user exists and by item access
        if (userRepository.findById(userId).isPresent() && !updatedItem.getOwner().getId().equals(userId))
            throw new NoSuchElementException("Access denied");
        //Check name
        String updatedName = item.getName();
        if (updatedName != null && !updatedName.isBlank())
            updatedItem.setName(updatedName);
        //Check description
        String updatedDescription = item.getDescription();
        if (updatedDescription != null && !updatedDescription.isBlank()) {
            updatedItem.setDescription(updatedDescription);
        }
        //Check available
        if (item.getAvailable() != null) {
            updatedItem.setAvailable(item.getAvailable());
        }
        return updatedItem;
    }
}
