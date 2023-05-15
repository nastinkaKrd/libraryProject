package com.libraryProject.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//@Entity
//@Table(name = "users")
public enum UserRoles {
    ROLE_LIBRARIAN, ROLE_ADMIN, ROLE_USER;

}
