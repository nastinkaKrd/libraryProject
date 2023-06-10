package com.library.project.models;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @Column(name = "author_id")
    private int authorId;

    @Column(name = "name")
    private String name;



}
