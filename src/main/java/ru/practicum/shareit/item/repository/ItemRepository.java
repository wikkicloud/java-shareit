package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.util.BaseRepository;

import java.util.List;

public interface ItemRepository extends BaseRepository<ItemDto> {
    List<ItemDto> getAllByUser(long userId);

    List<ItemDto> searchByText(String text);
}
