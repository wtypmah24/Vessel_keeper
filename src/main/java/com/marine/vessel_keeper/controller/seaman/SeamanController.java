package com.marine.vessel_keeper.controller.seaman;

import com.marine.vessel_keeper.dto.request.SeamanRequestDto;
import com.marine.vessel_keeper.dto.response.SeamanResponseDto;
import com.marine.vessel_keeper.exception.SeamanException;
import com.marine.vessel_keeper.exception.VesselException;
import com.marine.vessel_keeper.service.seaman.SeamanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/seamen")
@Tag(name = "Seaman Controller", description = "Here you can manage you labor pool.")
public class SeamanController {
    private final SeamanService seamanService;

    @Autowired
    public SeamanController(SeamanService seamanService) {
        this.seamanService = seamanService;
    }

    @GetMapping
    public List<SeamanResponseDto> getAllSeamen() {
        return seamanService.getAllSeamen();
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
    @Operation(
            summary = "Sign on a seaman on a vessel.",
            description = "Allows you sign on the seaman you choose on the vessel you choose."
    )
    public ResponseEntity<Set<SeamanResponseDto>> hireSeaman(@PathVariable long seamanId,
                                                             @PathVariable long vesselId) throws SeamanException {
        return ResponseEntity.status(HttpStatus.OK).body(seamanService.hireSeaman(seamanId, vesselId));
    }

    @PostMapping("/signOff/")
    @Operation(
            summary = "Sign off a seaman from a vessel.",
            description = "Allows you sign off the seaman you choose from the vessel. Seaman evaluation report is compulsory!"
    )
    public ResponseEntity<Set<SeamanResponseDto>> signOffSeaman(@RequestParam("seamanId") long seamanId,
                                                                @RequestParam("imoNumber") long imoNumber,
                                                                @RequestParam("comment") String comment) {
        return ResponseEntity.status(HttpStatus.OK).body(seamanService.signOffSeaman(seamanId, imoNumber, comment));
    }

    @PostMapping("/changeCrew/")
    @Operation(
            summary = "Perform crew change.",
            description = "Allows you replace one seaman on another. Evaluation report to signing off seaman is compulsory!"
    )
    public ResponseEntity<Set<SeamanResponseDto>> changeCrew(@RequestParam("signOnId") long signOnId,
                                                             @RequestParam("signOffId") long signOffId,
                                                             @RequestParam("vesselId") long vesselId,
                                                             @RequestParam("comment") String comment) throws SeamanException {
        return ResponseEntity.status(HttpStatus.OK).body(seamanService.changeCrew(signOnId, signOffId, vesselId, comment));
    }

    @PostMapping("/findApplicableSeaman/{imoNumber}")
    @Operation(
            summary = "Find applicable seaman to the vessel you choose.",
            description = "Allows you find applicable seaman to the vessel based on previous seaman's experience."
    )
    public ResponseEntity<Set<SeamanResponseDto>> findApplicableSeamen(@PathVariable long imoNumber) throws VesselException {
        return ResponseEntity.status(HttpStatus.OK).body(seamanService.findApplicableSeamenToVessel(imoNumber));
    }
}
