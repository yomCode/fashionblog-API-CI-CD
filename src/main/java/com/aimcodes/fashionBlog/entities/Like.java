package com.aimcodes.fashionBlog.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "likes")
public class Like extends BaseEntity{

    @Column(name = "UUID", unique = true)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @OneToOne
    @JoinColumn(name = "user_id")
    @Nullable
    private User user;
}
