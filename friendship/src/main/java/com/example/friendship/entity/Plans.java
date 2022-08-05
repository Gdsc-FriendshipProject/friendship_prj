package com.example.friendship.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plan_tb2")

public class Plans {

    @Id //plan 객체 pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plan_no;

    @Column(length = 30, nullable = false)  //일정 제  목
    private String plan_title;

    @Column(length = 200)       //일정 세부사항
    private String plan_memo;

    @Column(length = 30)        //일정 날짜(노는 날)
    private String plan_date;

    @Column(length = 50)    //이미지 주소값
    private String plan_img;

    @OneToMany(mappedBy = "plans", cascade = CascadeType.ALL, orphanRemoval = true)  //조회할 때마다 place 테이블 가져옴
    private List<Places> places = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;      //plan 생성 시간

    @UpdateTimestamp
    private LocalDateTime updatedDate;      //plan 수정 시간



   @Builder
    public Plans(String plan_title, String plan_memo, String plan_date, String plan_img){
       this.plan_title = plan_title;
       this.plan_memo = plan_memo;
       this.plan_date = plan_date;
       this.plan_img = plan_img;
   }

   public static Plans createPlans(String plan_title, String plan_memo, String plan_date, String plan_img){
       return Plans.builder()
               .plan_title(plan_title)
               .plan_date(plan_date)
               .plan_img(plan_img)
               .plan_memo(plan_memo)
               .build();
   }

   public void putPlace(Places places){
       this.places.add((places));
   }


}


















