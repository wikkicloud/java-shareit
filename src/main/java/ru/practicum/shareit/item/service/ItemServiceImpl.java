package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Item create(long userId, Item item) {
        User owner = userRepository.getById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        item.setOwner(owner);
        log.info("Create Item {}", item);
        return itemRepository.create(item);
    }

    @Override
    public Item update(long userId, long itemId, Item item) {
        Item updatedItem = getValidItemDto(userId, itemId, item);
        log.info("Update Item {}", updatedItem);
        return itemRepository.update(itemId, updatedItem);
    }

    @Override
    public Item getById(long id) {
        return itemRepository.getById(id).orElseThrow(() -> new NoSuchElementException("Item not found"));
    }

    @Override
    public List<Item> searchByText(String text) {
        if (text != null && !text.isBlank())
            return itemRepository.searchByText(text.toLowerCase(Locale.ROOT));
        //Return empty List
        return new ArrayList<>();
    }

    @Override
    public List<Item> getAllByUser(long userId) {
        return itemRepository.getAllByUser(userId);
    }

    private Item getValidItemDto(long userId, long itemId, Item item) {
        Item updatedItem = itemRepository.getById(itemId).orElseThrow(
                () -> new NoSuchElementException("Item not found"));
        // Check user exists and by item access
        if (userRepository.getById(userId).isPresent() && !updatedItem.getOwner().getId().equals(userId))
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
