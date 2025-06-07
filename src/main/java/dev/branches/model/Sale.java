package dev.branches.model;

import jakarta.persistence.*;
import lombok.*;

@With
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
    @Column(nullable = false, columnDefinition = "DECIMAL")
    private Double totalValue;
}
