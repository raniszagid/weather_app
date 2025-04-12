package ru.spbpu.weather.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Entity
@Getter
@Table(name = "favourite_requests")
public class FavouriteRequestEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "city")
    private String city;
}