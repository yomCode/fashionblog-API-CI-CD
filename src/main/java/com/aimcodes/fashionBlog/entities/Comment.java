package com.aimcodes.fashionBlog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "comments")
public class Comment extends BaseEntity{

    @Column(name = "UUID", unique = true)
    private String uuid;

    @Column(name = "contents", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private String comment_author;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @JsonIgnore
    @OneToMany(mappedBy = "comment", orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Like> likes;

}
