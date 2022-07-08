package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> repository = new HashMap<>();
    private long id = 0L;

    @Override
    public List<User> getAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<User> getById(long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public User create(User user) {
        user.setId(genId());
        repository.put(id, user);
        return user;
    }

    @Override
    public void remove(long id) {
        repository.remove(id);
    }

    @Override
    public User update(long id, User user) {
        repository.put(id, user);
        return user;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    private Long genId() {
        return ++id;
    }
}
