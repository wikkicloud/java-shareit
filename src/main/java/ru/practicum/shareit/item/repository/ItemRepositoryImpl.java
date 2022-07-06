package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.util.BaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl extends BaseEntity implements ItemRepository {
    private final Map<Long, ItemDto> repository = new HashMap<>();
    private long id = 0L;

    @Override
    public List<ItemDto> getAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<ItemDto> getById(long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public ItemDto create(ItemDto itemDto) {
        itemDto.setId(genId());
        repository.put(id, itemDto);
        return itemDto;
    }

    @Override
    public List<ItemDto> searchByText(String text) {
        return getAll().stream()
                .filter(ItemDto::getAvailable)
                .filter(itemDto -> itemDto.getName().toLowerCase(Locale.ROOT).contains(text) ||
                        itemDto.getDescription().toLowerCase(Locale.ROOT).contains(text))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getAllByUser(long userId) {
        return getAll().stream()
                .filter(itemDto -> itemDto.getOwner().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(long id) {
        repository.remove(id);
    }

    @Override
    public ItemDto update(long id, ItemDto itemDto) {
        repository.put(id, itemDto);
        return itemDto;
    }

    private Long genId() {
        return ++id;
    }
}
