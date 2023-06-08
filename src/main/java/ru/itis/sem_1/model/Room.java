package ru.itis.sem_1.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;
    private Integer countRoom;
    private String description;
    private Integer square;



    @ManyToOne
    @JoinColumn(name = "type")
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    private List<RoomOption> services;

    @ManyToMany
    private List<User> renters;

    public enum Status {
        ACTIVE, INACTIVE, DELETED
    }

    private String imagePath;
    private String city;
    private String address;


    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;



    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;


    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
