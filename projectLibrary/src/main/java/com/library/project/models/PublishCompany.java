package com.library.project.models;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "publish_companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublishCompany {
    @Id
    @Column(name = "publish_company_id")
    private int publishCompanyId;
    @Basic
    @Column(name = "name_of_company")
    private String nameOfCompany;
}
