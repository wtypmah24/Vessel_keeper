package com.marine.vessel_keeper.controller.vessel;

import com.marine.vessel_keeper.dto.request.VesselRequestDto;
import com.marine.vessel_keeper.dto.response.VesselResponseDto;
import com.marine.vessel_keeper.exception.UserException;
import com.marine.vessel_keeper.exception.VesselException;
import com.marine.vessel_keeper.exception.VoyageException;
import com.marine.vessel_keeper.service.vessel.VesselService;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/vessel")
@PreAuthorize("hasAnyAuthority('OPERATIONAL_MANAGER', 'OWNER')")
public class VesselController {
    private final VesselService vesselService;

    @Autowired
    public VesselController(VesselService vesselService) {
        this.vesselService = vesselService;
    }

    @PreAuthorize("hasAnyAuthority('OPERATIONAL_MANAGER', 'OWNER')")
    @PostMapping("/add")
    public ResponseEntity<VesselResponseDto> addVessel(@RequestBody VesselRequestDto vesselRequestDto) throws VesselException {
        return ResponseEntity.status(HttpStatus.CREATED).body(vesselService.addVessel(vesselRequestDto));
    }

    @PreAuthorize("hasAnyAuthority('OPERATIONAL_MANAGER', 'OWNER')")
    @GetMapping("get_all")
    public ResponseEntity<Set<VesselResponseDto>> getAllVessels() {
        return ResponseEntity.status(HttpStatus.OK).body(vesselService.getAllVessels());
    }

    @PreAuthorize("hasAnyAuthority('OPERATIONAL_MANAGER', 'OWNER')")
    @DeleteMapping("/remove/{imoNumber}")
    public void removeVessel(@PathVariable long imoNumber) throws VesselException {
        vesselService.deleteVessel(imoNumber);
    }

    @PreAuthorize("hasAnyAuthority('OPERATIONAL_MANAGER', 'OWNER')")
    @GetMapping("/find_applicable/{voyageId}")
    public ResponseEntity<Set<VesselResponseDto>> findApplicable(@PathVariable long voyageId) throws VoyageException {
        return ResponseEntity.status(HttpStatus.OK).body(vesselService.findApplicableVesselToVoyage(voyageId));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(VesselException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
