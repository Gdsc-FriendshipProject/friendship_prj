package com.example.friendship.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PlansDto {

    @NotBlank(message = "제목을 입력하세요.")
    private String plan_title;

    @NotBlank(message = "일정을 소개하세요.")
    private String plan_memo;

    @NotBlank(message = "날짜를 선택하세요.")
    private String plan_date;

    @NotBlank(message = "이미지를 골라주세요.")
    private String plan_img;

    private List<PlacesDto> places;

}
