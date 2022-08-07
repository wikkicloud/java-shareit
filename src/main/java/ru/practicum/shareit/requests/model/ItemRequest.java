package ru.practicum.shareit.requests.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.user.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "item_requests")
@Getter
@Setter
@ToString
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    private LocalDateTime created;
}
