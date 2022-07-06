package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
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
    public ItemDto create(long userId, ItemDto itemDto) {
        userRepository.getById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        ItemDto.User owner = new ItemDto.User();
        owner.setId(userId);

        itemDto.setOwner(owner);
        log.info("Create Item {}", itemDto);
        return itemRepository.create(itemDto);
    }

    @Override
    public ItemDto update(long userId, long id, ItemDto itemDto) {
        ItemDto updatedItem = getValidItemDto(userId, id, itemDto);
        log.info("Update Item {}", updatedItem);
        return itemRepository.update(id, updatedItem);
    }

    @Override
    public ItemDto getById(long id) {
        return itemRepository.getById(id).orElseThrow(() -> new NoSuchElementException("Item not found"));
    }

    @Override
    public List<ItemDto> searchByText(String text) {
        if (text != null && !text.isBlank())
            return itemRepository.searchByText(text.toLowerCase(Locale.ROOT));
        //Return empty List
        return new ArrayList<>();
    }

    @Override
    public List<ItemDto> getAllByUser(long userId) {
        return itemRepository.getAllByUser(userId);
    }

    private ItemDto getValidItemDto(long userId, long id, ItemDto itemDto) {
        ItemDto updatedItem = itemRepository.getById(id).orElseThrow(
                () -> new NoSuchElementException("Item not found"));
        // Check user exists and by item access
        if (userRepository.getById(userId).isPresent() && !updatedItem.getOwner().getId().equals(userId))
            throw new NoSuchElementException("Access denied");
        //Check name
        String updatedName = itemDto.getName();
        if (updatedName != null && !updatedName.isBlank())
            updatedItem.setName(updatedName);
        //Check description
        String updatedDescription = itemDto.getDescription();
        if (updatedDescription != null && !updatedDescription.isBlank()) {
            updatedItem.setDescription(updatedDescription);
        }
        //Check available
        if (itemDto.getAvailable() != null) {
            updatedItem.setAvailable(itemDto.getAvailable());
        }
        return updatedItem;
    }
}
