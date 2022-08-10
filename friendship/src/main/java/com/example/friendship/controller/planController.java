package com.example.friendship.controller;

import com.example.friendship.PlansService;
import com.example.friendship.dto.PlansDto;
import com.example.friendship.entity.Plans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:8081/")
public class planController {

    @Autowired
    private PlansService plansService;


    @PostMapping("addPlan")
    public ResponseEntity<String> createPlansWithPlaces(@RequestBody PlansDto plansDto) {

        plansService.createPlansWithPlaces(plansDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("생성 성공");
    }

    //검색기능
    @GetMapping("/list")                                            //default값 -> localhost:8080/list path만 줘도 알아서 0, 10으로 넘겨준 것으로 처리합니다.
    public String planList(Model model, @RequestParam String keyword , @PageableDefault(page = 0, size =9, sort = "pno", direction = Sort.Direction.DESC) Pageable pageable
    ){

        Page<Plans> plans = plansService.getAllPlans(pageable);         //일정 전부 가져옴
        /*if(keyword == null) {     //검색어가 없으면 일정 전부 전달
            plans = plansService.getAllPlans(pageable);
        }
        else {              //검색어가 있으면
            plans = plansService.searchKeyword(places, keyword, pageable);
        }
    */
        //Page<Plans> plans = plansService.searchKeyword(keyword, pageable);
        //System.out.println(plans.getContent().size());
        //model.addAttribute("Plans", plans.getContent().get(0).getPlaces());   //프론트단으로 검색에 해당되는 일정 전달
        //System.out.println(plans.getContent().getClass().getName()); //

        return "/index";
    }
}

