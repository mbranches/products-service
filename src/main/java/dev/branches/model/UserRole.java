package dev.branches.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@Entity(name = "user_role")
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private Role role;
}
