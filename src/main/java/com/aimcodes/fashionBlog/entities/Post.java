package com.aimcodes.fashionBlog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity{

    @Column(name = "UUID", unique = true)
    private String uuid;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name = "contents", nullable = false)
    private String content;

    @JsonIgnore
    @OneToMany(mappedBy = "post", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_category", nullable = false)
    private Category category;

}
