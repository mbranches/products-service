package dev.branches.model;

import dev.branches.dto.request.ProductPostRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, columnDefinition = "DECIMAL")
    private Double unitPrice;

    public static Product of(ProductPostRequest postRequest) {
        return Product.builder()
                .name(postRequest.name())
                .unitPrice(postRequest.unitPrice())
                .build();
    }
}
