package aiss.GitMiner.controller;

import aiss.GitMiner.model.Project;
import aiss.GitMiner.model.User;
import aiss.GitMiner.repository.UserRepository;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Tag(name="User", description = "User management API")
@RestController
@RequestMapping("/gitminer/users")
public class UserController {

    @Autowired
    UserRepository userRepository;


    @Operation(summary="retrieve User",description="returns all existent User", tags={"User","get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class))}, description = "Successfully retrieved Users")
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Operation(summary="retrieve User by id",description="returns the User with the specified id", tags={"User","get","id"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class))}, description = "Successfully retrieved User"),
            @ApiResponse(responseCode ="404" ,content = {@Content(schema = @Schema())},description = "User not found")
    })
    @GetMapping("/{id}")
    public User getUserById(@Parameter(description = "id of the User to be obtained",required = true)@PathVariable String id) {
        if(!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User with id " + id + " does not exist");
        }
        return userRepository.findById(id).get();
    }



    @Operation(summary="insert a User",description="add a new User whose data is passed in the body of the request in JSON format", tags={"User","post"})
    @ApiResponses ({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        User newUser = userRepository.save(
                new User(
                        user.getId(),
                        user.getUsername(),
                        user.getName(),
                        user.getAvatarUrl(),
                        user.getWebUrl()
                )
        );
        return newUser;
    }


    @Operation(summary="update a User",description="update a User data by specifying its id and the new data in the body of the request in JSON format", tags={"User","put"})
    @ApiResponses ({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody User user, @Parameter(description = "id of the User to be modified", required = true)@PathVariable String id) {
        if(!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User with id " + id + " does not exist");
        }
        User _user = userRepository.findById(id).get();
        _user.setId(user.getId());
        _user.setUsername(user.getUsername());
        _user.setName(user.getName());
        _user.setAvatarUrl(user.getAvatarUrl());
        _user.setWebUrl(user.getWebUrl());
        userRepository.save(_user);
    }


    @Operation(summary="delete a User",description="delete a User whose id is specified", tags={"User","delete"})
    @ApiResponses ({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "id of the User to be deleted", required = true)@PathVariable String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User with id " + id + " does not exist");
        }
    }
}
