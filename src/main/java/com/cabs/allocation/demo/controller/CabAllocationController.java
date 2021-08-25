package com.cabs.allocation.demo.controller;

import com.cabs.allocation.demo.model.CabView;
import com.cabs.allocation.demo.model.SearchOptions;
import com.cabs.allocation.demo.service.CabAllotmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 Sample request

 HTTP: POST
 {
 "cabTypes":["NORMAL"],
 "location":{
 "latitude": 18.990438,
 "longitude": 73.150000
 }
 }
 */
@RestController
@RequestMapping("/allocation-service/v1.0/find-cabs")
public class CabAllocationController {

    @Resource
    CabAllotmentService allotmentService;

    @PostMapping
    public List<CabView> findNearbyCabs(@RequestBody SearchOptions options) {
        return allotmentService.findNearByCabs(options);
    }
}



// 19.157655, 72.999228 Airoli Station
// 19.157934, 72.993477 Airoli Sector 16
// 19.143114, 72.996668 Yashodeep Heights
// 19.160438, 72.994834 Sector 4 Bridge