package ru.practicum.shareit.util;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    List<T> getAll();
    Optional<T> getById(long id);
    T create(T t);
    void remove(long id);
    T update(long id, T t);
}
