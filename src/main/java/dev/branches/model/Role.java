package dev.branches.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType name;
    @Column(nullable = false)
    private String description;

    public enum RoleType {
        ADMIN,
        BASIC,
        MANAGER
    }
}
