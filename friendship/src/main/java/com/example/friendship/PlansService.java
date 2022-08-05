package com.example.friendship;

import com.example.friendship.dto.PlacesDto;
import com.example.friendship.dto.PlansDto;
import com.example.friendship.entity.Places;
import com.example.friendship.entity.Plans;
import com.example.friendship.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlansService {

    private final PlanRepository planRepository;

   public void createPlansWithPlaces(PlansDto plansDto){

       Plans plans = Plans.createPlans(
               plansDto.getPlan_title(), plansDto.getPlan_memo(), plansDto.getPlan_date(), plansDto.getPlan_img());

       List<PlacesDto> places = plansDto.getPlaces();

       for(PlacesDto placesDto: places){
           Places place = Places.createPlaces(placesDto.getName(), placesDto.getMapx(), placesDto.getMapy(), plans);
           plans.putPlace(place);
       }

       planRepository.save(plans);
   }

}
