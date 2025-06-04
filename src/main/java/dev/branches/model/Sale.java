package dev.branches.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Product product;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false, columnDefinition = "DECIMAL")
    private Double totalValue;
}
