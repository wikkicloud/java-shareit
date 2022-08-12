package ru.practicum.shareit.requests.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.dto.ItemRequestMapper;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.util.Constant.USER_ID_HEADER;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;
    private final UserService userService;
    private final ItemService itemService;

    @PostMapping
    public ItemRequestDto create(
            @RequestHeader(USER_ID_HEADER) long userId,
            @Valid @RequestBody ItemRequestDto itemRequestDto
    ) {
        User requester = userService.getById(userId);
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto, requester);
        return ItemRequestMapper.toItemRequestDto(itemRequestService.create(itemRequest), null);
    }

    @GetMapping
    public List<ItemRequestDto> findByOwnerItemRequest(@RequestHeader(USER_ID_HEADER) long requesterId) {
        return itemRequestService.findByRequesterId(requesterId).stream()
                .map(itemRequest -> {
                    List<Item> items = itemService.findByRequestId(itemRequest.getId());
                    return ItemRequestMapper.toItemRequestDto(itemRequest, items);
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<ItemRequestDto> findAllItemRequest(
            @RequestHeader(USER_ID_HEADER) long userId,
            @RequestParam(defaultValue = "0", required = false) int from,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {
        return itemRequestService.findAll(userId, from, size).stream()
                .map(itemRequest -> {
                    List<Item> items = itemService.findByRequestId(itemRequest.getId());
                    return ItemRequestMapper.toItemRequestDto(itemRequest, items);
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ItemRequestDto getById(@RequestHeader(USER_ID_HEADER) long userId, @PathVariable long id) {
        //Check user
        userService.getById(userId);
        ItemRequest itemRequest = itemRequestService.findById(id);
        List<Item> items = itemService.findByRequestId(id);
        return ItemRequestMapper.toItemRequestDto(itemRequest, items);
    }

}
