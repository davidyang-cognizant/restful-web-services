package com.example.restfulwebservices.user;

import com.example.restfulwebservices.repository.PostRepository;
import com.example.restfulwebservices.repository.UserRepository;
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
import java.util.Optional;

@RestController
public class UserControllerJPA {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    @GetMapping("jpa/users")
    public List<EntityModel<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
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

    @GetMapping("jpa/users/{id}")
    public EntityModel<User> getUserById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id:" + id);
        }
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
        WebMvcLinkBuilder linkBuilder2 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrievePostsForUser(id));
        entityModel.add(linkBuilder.withRel("all-users"));
        entityModel.add(linkBuilder2.withRel("user-posts"));
        return entityModel;
    }

    @DeleteMapping("jpa/users/{id}")
    public void deleteUserById(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("jpa/users")
    public ResponseEntity<User> createUsers(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id:" + id);
        }
        return user.get().getPosts();
    }

    @PostMapping("jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id:" + id);
        }

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
