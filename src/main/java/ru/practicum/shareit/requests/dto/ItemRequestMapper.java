package ru.practicum.shareit.requests.dto;

import ru.practicum.shareit.requests.model.ItemRequest;

public class ItemRequestMapper {
    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        ItemRequestDto.User requester = new ItemRequestDto.User();
        if (itemRequest.getRequester() != null)
            requester.setId(itemRequest.getRequester().getId());
        return ItemRequestDto.builder()
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .requester(requester)
                .build();
    }
}
