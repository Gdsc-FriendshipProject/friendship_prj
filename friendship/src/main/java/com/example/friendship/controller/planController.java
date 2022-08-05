package com.example.friendship.controller;

import com.example.friendship.PlansService;
import com.example.friendship.dto.PlansDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
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
}

