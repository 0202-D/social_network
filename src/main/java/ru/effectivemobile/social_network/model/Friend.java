package ru.effectivemobile.social_network.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // Первый юзер предлагает дружбу и подписывается
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "first_user_id", referencedColumnName = "id")
    User firstUser;
    // Второй юзер на кого подписаны
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "second_user_id", referencedColumnName = "id")
    User secondUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    ClaimAsFriendStatus status;
}
