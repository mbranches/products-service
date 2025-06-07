package dev.branches.model;

import jakarta.persistence.*;
import lombok.*;

@With
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "sale_product")
public class SaleProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Sale sale;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;
    @Column(nullable = false)
    private Integer quantity;
    @Column(name = "total_value", nullable = false, columnDefinition = "DECIMAL")
    private Double totalValue;
}
