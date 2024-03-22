package com.marine.vessel_keeper.controller.vessel;

import com.marine.vessel_keeper.dto.request.VesselRequestDto;
import com.marine.vessel_keeper.dto.response.VesselResponseDto;
import com.marine.vessel_keeper.exception.VesselException;
import com.marine.vessel_keeper.service.vessel.VesselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/vessel")
public class VesselController {
    private final VesselService vesselService;
    @Autowired
    public VesselController(VesselService vesselService) {
        this.vesselService = vesselService;
    }
    @PostMapping("/add")
    public ResponseEntity<VesselResponseDto> addVessel(@RequestBody VesselRequestDto vesselRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(vesselService.addVessel(vesselRequestDto));
    }

    @GetMapping("get_all")
    public ResponseEntity<Set<VesselResponseDto>> getAllVessels(){
        return ResponseEntity.status(HttpStatus.OK).body(vesselService.getAllVessels());
    }

    @DeleteMapping("/remove/{imoNumber}")
    public void removeVessel(@PathVariable long imoNumber) throws VesselException {
        vesselService.deleteVessel(imoNumber);
    }

    @GetMapping("/find_applicable/{voyageId}")
    public ResponseEntity<Set<VesselResponseDto>> findApplicable(@PathVariable long voyageId){
        return ResponseEntity.status(HttpStatus.OK).body(vesselService.findApplicableVesselToVoyage(voyageId));
    }
}
