package com.marine.vessel_keeper.controller.third.party;

import com.marine.vessel_keeper.dto.response.VesselApiDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vessel_api")
@PreAuthorize("hasAnyAuthority('OPERATIONAL_MANAGER', 'OWNER')")
public class ThirdPartyController {
    private final VesselApiClient vesselApiClient;

    @Value("${secret_key}")
    private String secretKey;

    public ThirdPartyController(VesselApiClient vesselApiClient) {
        this.vesselApiClient = vesselApiClient;
    }

    @GetMapping("/get_all/")
    @PreAuthorize("hasAnyAuthority('OPERATIONAL_MANAGER', 'OWNER')")
    public List<VesselApiDto> getAllVessels() {
        return vesselApiClient.getAllVessels(secretKey);
    }
}