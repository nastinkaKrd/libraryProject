package com.library.project.models;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "readers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reader {
    @Id
    @Column(name = "reader_id")
    private int readerId;
    @Basic
    @Column(name = "name")
    private String name;
}
