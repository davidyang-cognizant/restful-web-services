package com.example.restfulwebservices.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

/**
 * User Bean
 */
@Entity(name="user_details")
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Size(min=2, message ="Name should have at least 2 characters")
//    @JsonProperty("user_name") // used to customized name
    private String name;

    @Past(message =  "Birthday must be in the past")
    private LocalDate birthDate;

    // User can have many posts meaning their ID can show up multiple times in the table B (one user to multiple posts)
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Post> posts;

    protected User(){}

    public User(int id, String name, LocalDate birthdate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", posts=" + posts +
                '}';
    }
}
