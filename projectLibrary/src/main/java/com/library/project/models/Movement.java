package com.library.project.models;
import lombok.*;
import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movement {
    @Id
    @Column(name = "move_id")
    private int moveId;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "date")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "reader", referencedColumnName = "reader_id")
    private Reader reader;
    @ManyToOne
    @JoinColumn(name = "printed_element", referencedColumnName = "printed_element_id")
    private PrintedElement printedElement;
}
