package com.libraryProject.project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    //@GeneratedValue
    @Column(name = "token_id")
    private int tokenId;

    @Basic
    @Column(name = "value")
    private String value;

    @Basic
    @Column(name = "revoked")
    public boolean revoked;

    @Basic
    @Column(name = "expired")
    public boolean expired;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "user_id")
    private User user;
}
