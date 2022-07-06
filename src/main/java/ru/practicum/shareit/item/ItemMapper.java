package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static ItemDto toItemDto (Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.isAvailable());

        ItemDto.User owner = new ItemDto.User();
        owner.setId(item.getOwner().getId());
        itemDto.setOwner(owner);

        ItemDto.ItemRequest request = new ItemDto.ItemRequest();
        request.setId(item.getOwner().getId());
        itemDto.setRequest(request);

        return itemDto;
    }
}
