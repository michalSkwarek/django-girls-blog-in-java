package com.skwarek.blog.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Michal on 04/01/2017.
 */
@Entity
@Table(name = "comment")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"post"})
@ToString(exclude = {"author", "createdDate", "approvedComment", "post"})
public class Comment extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = -4790309532913180811L;

    private String author;

    @Type(type = "text")
    private String text;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "approved_comment")
    private boolean approvedComment;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
