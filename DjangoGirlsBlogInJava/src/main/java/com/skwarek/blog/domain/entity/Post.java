package com.skwarek.blog.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Michal on 02/01/2017.
 */
@Entity
@Table(name = "post")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"author"})
@ToString(exclude = {"author", "text", "createdDate", "publishedDate", "comments"})
public class Post extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1850901545913390632L;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private String title;

    @Type(type = "text")
    private String text;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "published_date")
    private Date publishedDate;

    @JsonManagedReference
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
}
