package ru.practicum.shareit.item.service;


import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(long userId, ItemDto itemDto);
    ItemDto update(long userId, long id, ItemDto itemDto);
    ItemDto getById(long id);
    List<ItemDto> getAllByUser(long userId);
    List<ItemDto> searchByText(String text);
}
