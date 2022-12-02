package com.aimcodes.fashionBlog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity{
    @Column(name = "UUID", unique = true)
    private String uuid;

    @Column(name = "category_name", nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", orphanRemoval = true ,fetch = FetchType.EAGER)
    private List<Post> posts;
}
