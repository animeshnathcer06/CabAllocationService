package com.cabs.allocation.demo.controller;

import com.cabs.allocation.demo.model.CabView;
import com.cabs.allocation.demo.model.Location;
import com.cabs.allocation.demo.service.TripService;
import com.cabs.allocation.demo.service.cabs.create.CabsPlacementService;
import com.cabs.allocation.demo.types.CabType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/allocation-service/v1.0/cabs")
public class CabsController {

    @Resource
    CabsPlacementService cabsPlacementService;

    @Resource
    TripService tripService;

    /**
     *
     Sample Request creates a new cab on the grid
     HTTP POST
     {
     "latitude": 19.148290,
     "longitude": 72.992720
     }
     */
    @PostMapping
    public CabView placeCabOnMap(@RequestBody Location location) {
        return cabsPlacementService.placeNewCabOnMap(location, CabType.NORMAL);
    }

    /**
     * Adds trip earned money to the cab
     *
     * Sample Request
     * HTTP GET
     * http://localhost:8080/allocation-service/v1.0/cabs/1/trip?tripMoney=5000
     *
     */
    @GetMapping("/{cabId}/trip")
    public CabView addTrip(@PathVariable long cabId, @RequestParam double tripMoney) {
        return tripService.addTrip(cabId, tripMoney);
    }
}


// 19.157655, 72.999228 Airoli Station
// 19.157934, 72.993477 Airoli Sector 16
// 19.143114, 72.996668 Yashodeep Heights
// 19.160438, 72.994834 Sector 4 Bridge Client Standing on
//    List<CabView> dummyCabView() {
//        return Arrays.asList(
//                new CabViewBuilder()
//                .setCabId(1)
//                .setDistanceFromClient(100)
//                        .setEarningsTillNow(1500)
//                        .build());
//    }