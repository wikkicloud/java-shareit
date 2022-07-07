package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.util.BaseRepository;

import java.util.List;

public interface ItemRepository extends BaseRepository<Item> {
    List<Item> getAllByUser(long userId);

    List<Item> searchByText(String text);
}
