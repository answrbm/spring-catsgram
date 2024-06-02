package ansarbektassov.catsgram.controller;

import ansarbektassov.catsgram.exceptions.InvalidEmailException;
import ansarbektassov.catsgram.exceptions.UserAlreadyExistsException;
import ansarbektassov.catsgram.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final HashMap<String, User> users;

    public UserController() {
        this.users = new HashMap<>();
    }

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User save(@RequestBody User user) {
        if(user.getEmail().isEmpty() || user.getEmail().isBlank() || user.getEmail() == null) {
            throw new InvalidEmailException("Invalid email");
        } else if(users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistsException("User with such email already exists");
        } else {
            users.put(user.getEmail(),user);
            return user;
        }
    }

    @PutMapping
    public User update(@RequestBody User updatedUser) {
        if(users.containsKey(updatedUser.getEmail())) {
            users.put(updatedUser.getEmail(),updatedUser);
        } else {
            save(updatedUser);
        }
        return updatedUser;
    }
}
