package com.example.friendship.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="place_tb3")
@ToString
public class Places {

    @Id
    @Column(name = "place_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeNo;

    @Column(name = "name")
    private String name;

    @Column(name = "mapx")
    private String mapx;

    @Column(name = "mapy")
    private String mapy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_no")
    private Plans plans;

    @Builder
    public Places(String name, String mapx, String mapy, Plans plans){
        this.name = name;
        this.mapx = mapx;
        this.mapy = mapy;
        this.plans = plans;
    }

    public static Places createPlaces(String name, String mapx, String mapy, Plans plans){
        return Places.builder()
                .name(name)
                .mapx(mapx)
                .mapy(mapy)
                .plans(plans)
                .build();
    }
}



