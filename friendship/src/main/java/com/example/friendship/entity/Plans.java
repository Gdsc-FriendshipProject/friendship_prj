package com.example.friendship.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plan_tb2")

public class Plans {

    @Id //plan 객체 pk
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long plan_no;

    @Column(length = 30, nullable = false)  //일정 제  목
    private String plan_title;

    @Column(length = 200)       //일정 세부사항
    private String plan_memo;

    @Column(length = 30)        //일정 날짜(노는 날)
    private String plan_date;

    @Column(length = 50)    //이미지 주소값
    private String plan_img;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)  //조회할 때마다 place 테이블 가져옴
    @JoinColumn(name="plan_no")
    private List<Place> places;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;      //plan 생성 시간

    @UpdateTimestamp
    private LocalDateTime updatedDate;      //plan 수정 시간



   @OneToMany
    @JoinColumn(name = "member_id")
    private List<User> users;

   @Builder
    public Plans(String plan_title, String plan_memo, String plan_date, String plan_img,
                 List<Place> places){
       this.plan_title = plan_title;
       this.plan_memo = plan_memo;
       this.plan_date = plan_date;
       this.plan_img = plan_img;
       this.places = places;
   }

}


















