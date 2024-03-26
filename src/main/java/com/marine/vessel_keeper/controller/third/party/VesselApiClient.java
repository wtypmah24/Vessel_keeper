package com.marine.vessel_keeper.controller.third.party;

import com.marine.vessel_keeper.dto.response.VesselApiDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "vesselApiClient", url = "http://localhost:8081/")
public interface VesselApiClient {
    @GetMapping(path = "/vessel_api/get_all/{key}")
    List<VesselApiDto> getAllVessels(@PathVariable String key);
}