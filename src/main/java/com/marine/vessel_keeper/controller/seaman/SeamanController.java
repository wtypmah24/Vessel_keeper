package com.marine.vessel_keeper.controller.seaman;

import com.marine.vessel_keeper.dto.request.SeamanRequestDto;
import com.marine.vessel_keeper.dto.response.SeamanResponseDto;
import com.marine.vessel_keeper.exception.SeamanException;
import com.marine.vessel_keeper.exception.VesselException;
import com.marine.vessel_keeper.service.seaman.SeamanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/seamen")
public class SeamanController {
    private final SeamanService seamanService;

    @Autowired
    public SeamanController(SeamanService seamanService) {
        this.seamanService = seamanService;
    }

    @PostMapping("/create")
    public ResponseEntity<SeamanResponseDto> createUser(@RequestBody SeamanRequestDto seamanCandidate) throws SeamanException {
        return ResponseEntity.status(HttpStatus.CREATED).body(seamanService.addSeamanToLaborPool(seamanCandidate));
    }

    @DeleteMapping("/remove/{seamanId}")
    public void removeSeaman(@PathVariable long seamanId) throws SeamanException {
        seamanService.removeSeamanFromLaborPool(seamanId);
    }

    @PostMapping("/hire/{seamanId}/{vesselId}")
    public ResponseEntity<Set<SeamanResponseDto>> hireSeaman(@PathVariable long seamanId,
                                                             @PathVariable long vesselId) throws SeamanException {
        return ResponseEntity.status(HttpStatus.OK).body(seamanService.hireSeaman(seamanId, vesselId));
    }

    @PostMapping("/signOff/")
    public ResponseEntity<Set<SeamanResponseDto>> signOffSeaman(@RequestParam("seamanId") long seamanId,
                                                                @RequestParam("imoNumber") long imoNumber,
                                                                @RequestParam("comment") String comment) {
        return ResponseEntity.status(HttpStatus.OK).body(seamanService.signOffSeaman(seamanId, imoNumber, comment));
    }

    @PostMapping("/changeCrew/")
    public ResponseEntity<Set<SeamanResponseDto>> changeCrew(@RequestParam("signOnId") long signOnId,
                                                             @RequestParam("signOffId") long signOffId,
                                                             @RequestParam("vesselId") long vesselId,
                                                             @RequestParam("comment") String comment) throws SeamanException {
        return ResponseEntity.status(HttpStatus.OK).body(seamanService.changeCrew(signOnId, signOffId, vesselId, comment));
    }

    @PostMapping("/findApplicableSeaman/{imoNumber}")
    public ResponseEntity<Set<SeamanResponseDto>> findApplicableSeamen(@PathVariable long imoNumber) throws VesselException {
        return ResponseEntity.status(HttpStatus.OK).body(seamanService.findApplicableSeamenToVessel(imoNumber));
    }
}
