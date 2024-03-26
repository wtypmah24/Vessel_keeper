package com.marine.vessel_keeper.controller.third.party;

import com.marine.vessel_keeper.dto.response.VesselApiDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vessel_api")
public class ThirdPartyController {
    private final VesselApiClient vesselApiClient;
    @Value("${secret_key}")
    private String secretKey;

    public ThirdPartyController(VesselApiClient vesselApiClient) {
        this.vesselApiClient = vesselApiClient;
    }

    @GetMapping("/get_all/{key}")
    public List<VesselApiDto> getAllVessels(@PathVariable("key") String key){
        return vesselApiClient.getAllVessels(secretKey);
    }
}