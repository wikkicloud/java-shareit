package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.requests.model.ItemRequest;

import java.util.List;

public interface ItemRequestService {
    ItemRequest create(ItemRequest itemRequest);

    ItemRequest findById(long id);

    List<ItemRequest> findByRequesterId(long requesterId);

    List<ItemRequest> findAll(long userId, int from, int size);
}
