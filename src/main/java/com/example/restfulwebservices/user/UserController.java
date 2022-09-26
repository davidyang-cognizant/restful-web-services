package com.example.restfulwebservices.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("users")
    public List<EntityModel<User>> getAllUsers() {
        List<User> users = userDaoService.findAll();
        List<EntityModel<User>> usersWithLink = new ArrayList<>();
        for (User user:
             users) {
            EntityModel<User> entityModel = EntityModel.of(user);
            WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUserById(user.getId()));
            entityModel.add(linkBuilder.withRel("get-user-by-id"));
            usersWithLink.add(entityModel);
        }
        return usersWithLink;
    }

    @GetMapping("users/{id}")
    public EntityModel<User> getUserById(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("id:" + id);
        }
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
        entityModel.add(linkBuilder.withRel("all-users"));
        return entityModel;
    }

    @DeleteMapping("users/{id}")
    public void deleteUserById(@PathVariable int id) {
        userDaoService.deleteById(id);
    }

    @PostMapping("users")
    public ResponseEntity<User> createUsers(@Valid @RequestBody User user) {
        User savedUser = userDaoService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
