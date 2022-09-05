package ru.practicum.shareit.tests.integration;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceIntegrationTest {
    private final UserService userService;

    @Test
    public void getAll() {
        User user = userService.create(new User(null, "Name", "test@test.ru"));
        User user2 = userService.create(new User(null, "Name 2", "test1@test.ru"));
        User user3 = userService.create(new User(null, "Name 3", "test2@test.ru"));

        List<User> userList = userService.getAll();
        Assertions.assertEquals(3, userList.size());
        Assertions.assertEquals(user.getName(), userList.get(0).getName());
        Assertions.assertEquals(user2.getName(), userList.get(1).getName());
        Assertions.assertEquals(user3.getName(), userList.get(2).getName());
    }
}
