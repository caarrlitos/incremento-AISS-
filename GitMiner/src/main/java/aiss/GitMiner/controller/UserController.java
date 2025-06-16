package aiss.GitMiner.controller;

import aiss.GitMiner.model.User;
import aiss.GitMiner.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {return userRepository.findAll();}

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        User newUser = userRepository.save(new User(user.getId(), user.getUsername(),user.getName(), user.getAvatarUrl(), user.getWebUrl()));
        return newUser;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody User user, @PathVariable String id) {
        Optional<User> userOptional = userRepository.findById(id);
        User _user = userOptional.get();
        _user.setId(user.getId());
        _user.setUsername(user.getUsername());
        _user.setName(user.getName());
        _user.setAvatarUrl(user.getAvatarUrl());
        _user.setWebUrl(user.getWebUrl());
        userRepository.save(_user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }
}
