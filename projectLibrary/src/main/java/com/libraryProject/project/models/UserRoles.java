package com.libraryProject.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public enum UserRoles {
    ROLE_LIBRARIAN(1), ROLE_ADMIN(2), ROLE_USER(3);
    @Id
    @Column(name = "user_id")
    int roleId;
    UserRoles(int roleId){
        this.roleId = roleId;
    }
}
