package com.example.friendship.entity;

import javax.persistence.*;


@Entity
public class Place {

    @Id
    @Column(name="place_tb3")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long placeNo;

    @Column(length = 30)
    private String placeName;

    @Column(length = 30)
    private String mapX;

    @Column(length = 30)
    private String mapY;

    @Column(name="plan_no")     //plan pk
    private Long plan_no;


}

