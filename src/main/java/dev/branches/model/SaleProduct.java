package dev.branches.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity(name = "sale_product")
@NoArgsConstructor
@AllArgsConstructor
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
