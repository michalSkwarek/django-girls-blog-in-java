package com.skwarek.blog.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michal on 04/01/2017.
 */
@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"password", "enabled", "role", "posts"})
public class User extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 8342040766322570609L;

    private String username;

    private String password;

    private Boolean enabled;

    private String role;

    @JsonManagedReference
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "author")
    private List<Post> posts = new ArrayList<>();
}
