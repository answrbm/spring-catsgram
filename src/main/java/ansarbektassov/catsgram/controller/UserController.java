package ansarbektassov.catsgram.controller;

import ansarbektassov.catsgram.exceptions.InvalidEmailException;
import ansarbektassov.catsgram.exceptions.UserAlreadyExistsException;
import ansarbektassov.catsgram.exceptions.UserNotFoundException;
import ansarbektassov.catsgram.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final HashMap<String, User> users;

    public UserController() {
        this.users = new HashMap<>();
    }

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @GetMapping("/{email}")
    public User findByEmail(@PathVariable("email") String email) {
        return users.values().stream().filter(user -> user.getEmail().equals(email)).findFirst()
                .orElseThrow(() -> new UserNotFoundException("User with such email not found"));
    }

    @PostMapping
    public User save(@RequestBody User user) {
        log.debug("Сохраняем пользователя");
        if(user.getEmail().isEmpty() || user.getEmail().isBlank() || user.getEmail() == null) {
            throw new InvalidEmailException("Invalid email");
        } else if(users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistsException("User with such email already exists");
        } else {
            users.put(user.getEmail(),user);
            log.debug("Пользователь сохранен");
            return user;
        }
    }

    @PutMapping
    public User update(@RequestBody User updatedUser) {
        log.debug("Обновляем пользователя");
        if(users.containsKey(updatedUser.getEmail())) {
            users.put(updatedUser.getEmail(),updatedUser);
            log.debug("Пользователь обновлен");
        } else {
            save(updatedUser);
            log.debug("Нет такого пользователя, пользователь создан");
        }
        return updatedUser;
    }
}
