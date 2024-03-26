package com.marine.vessel_keeper.controller.voyage;

import com.marine.vessel_keeper.dto.request.VoyageRequestDto;
import com.marine.vessel_keeper.dto.response.VoyageResponseDto;
import com.marine.vessel_keeper.exception.UserException;
import com.marine.vessel_keeper.exception.VesselException;
import com.marine.vessel_keeper.exception.VoyageException;
import com.marine.vessel_keeper.service.voyage.VoyageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/voyage")
@Tag(name = "Voyage controller.",
        description = "Here you can manage voyages.")
public class VoyageController {
    private final VoyageService voyageService;

    @Autowired
    public VoyageController(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @PostMapping("/add")
    public ResponseEntity<VoyageResponseDto> addVoyage(@RequestBody VoyageRequestDto voyageRequestDto) throws VoyageException {
        return ResponseEntity.status(HttpStatus.CREATED).body(voyageService.addNewVoyage(voyageRequestDto));
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<VoyageResponseDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(voyageService.getAllVoyages());
    }

    @GetMapping("/get_available")
    public ResponseEntity<List<VoyageResponseDto>> getAllAvailable() {
        return ResponseEntity.status(HttpStatus.OK).body(voyageService.getAllAvailableVoyages());
    }

    @PostMapping("/assign")
    public ResponseEntity<VoyageResponseDto> assignVoyageToVessel(@RequestParam("voyageId") long voyageId,
                                                                  @RequestParam("imoNumber") long imoNumber) throws VoyageException, VesselException {
        return ResponseEntity.status(HttpStatus.OK).body(voyageService.assignVoyageToVessel(voyageId, imoNumber));
    }

    @GetMapping("/get_end")
    public ResponseEntity<List<VoyageResponseDto>> getVoyagesByDischargingPortAndEndDate(@RequestParam("dischargingPort") String dischargingPort,
                                                                                         @RequestParam("endDate") LocalDate endDate) {
        return ResponseEntity.status(HttpStatus.OK).body(voyageService.getVoyagesByDischargingPortAndEndDate(dischargingPort, endDate));
    }

    @GetMapping("/get_start")
    public ResponseEntity<List<VoyageResponseDto>> getVoyagesByLoadingPortAndStartDate(@RequestParam("loadingPort") String loadingPort,
                                                                                       @RequestParam("startDate") LocalDate startDate) {
        return ResponseEntity.status(HttpStatus.OK).body(voyageService.getVoyagesByLoadingPortAndStartDate(loadingPort, startDate));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> voyageHandleException(VoyageException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> vesselHandleException(VesselException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
