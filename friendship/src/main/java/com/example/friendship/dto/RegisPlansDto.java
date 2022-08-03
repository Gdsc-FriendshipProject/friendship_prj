package com.example.friendship.dto;

import com.example.friendship.entity.Plans;
import com.example.friendship.entity.Place;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor


public class RegisPlansDto {

    private List<Place> places;

    @NotBlank(message = "제목을 입력하세요.")
    private String plan_title;

    @NotBlank(message = "일정을 소개하세요.")
    private String plan_memo;

    @NotBlank(message = "날짜를 선택하세요.")
    private String plan_date;

    @NotBlank(message = "이미지를 골라주세요.")
    private String plan_img;

        Plans build = Plans.builder()
                .plan_title(plan_title)
                .plan_memo((plan_memo))
                .plan_date((plan_date))
                .plan_img((plan_img))
                .places(places)
                .build();


    }
