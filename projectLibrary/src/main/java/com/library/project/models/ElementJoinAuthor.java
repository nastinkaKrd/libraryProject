package com.library.project.models;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "elements_join_authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElementJoinAuthor {
   @Id
   @Column(name = "join_id")
   private int joinId;
   @ManyToOne
   @JoinColumn(name = "author", referencedColumnName = "author_id")
   private Author authorId;
   @ManyToOne
   @JoinColumn(name = "printed_element", referencedColumnName = "printed_element_id")
   private PrintedElement printedElementId;
}
