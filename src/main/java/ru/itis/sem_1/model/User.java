package ru.itis.sem_1.model;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.management.relation.Role;
import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String city;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER, ADMIN, MODERATOR
    }

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;



    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
