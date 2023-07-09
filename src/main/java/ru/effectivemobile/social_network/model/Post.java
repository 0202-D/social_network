package ru.effectivemobile.social_network.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String text;

    String header;

    @Column(name = "content")
    private byte [] data;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    LocalDateTime createdAt;
}
