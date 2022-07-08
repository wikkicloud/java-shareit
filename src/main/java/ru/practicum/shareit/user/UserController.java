package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll().stream()
                .map(user -> UserMapper.toUserDto(user))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable long id) {
        return UserMapper.toUserDto(userService.getById(id));
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(
                userService.create(user)
        );
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable long id, @RequestBody User user) {
        return UserMapper.toUserDto(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable long id) {
         userService.remove(id);
    }
}
