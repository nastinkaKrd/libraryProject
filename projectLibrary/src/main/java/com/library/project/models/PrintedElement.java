package com.library.project.models;
import lombok.*;
import jakarta.persistence.*;


@Entity
@Table(name = "printed_elements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrintedElement {
    @Id
    @Column(name = "printed_element_id")
    private int elementId;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "type_of_element")
    private String type;
    @Basic
    @Column(name = "style")
    private String style;
    @Basic
    @Column(name = "amount_of_elements")
    private int amountOfElements;
    @Basic
    @Column(name = "year_of_publish")
    private int yearOfPublish;
    @Basic
    @Column(name = "num_of_publish")
    private int numOfPublish;
    @ManyToOne
    @JoinColumn(name = "publish_company", referencedColumnName = "publish_company_id")
    private PublishCompany publishCompany;
}
