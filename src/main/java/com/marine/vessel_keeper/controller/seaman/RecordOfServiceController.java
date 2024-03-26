package com.marine.vessel_keeper.controller.seaman;

import com.marine.vessel_keeper.exception.SeamanException;
import com.marine.vessel_keeper.exception.VesselException;
import com.marine.vessel_keeper.service.seaman.RecordOfServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service_record")
@PreAuthorize("hasAnyAuthority('CREW_MANAGER', 'OWNER')")
@Tag(name = "Seaman's record of services controller.",
        description = "Here you can add manually working experience to a seaman.")
public class RecordOfServiceController {
    private final RecordOfServiceService recordService;

    @Autowired
    public RecordOfServiceController(RecordOfServiceService recordService) {
        this.recordService = recordService;
    }

    @PreAuthorize("hasAnyAuthority('CREW_MANAGER', 'OWNER')")
    @PostMapping("/add")
    @Operation(
            summary = "Add record of service to a seaman",
            description = "Allows you add a working experience to a seaman."
    )
    public void addServiceRecordManually(@RequestParam("seamanId") long seamanId,
                                         @RequestParam("imoNumber") long imoNumber,
                                         @RequestParam("comment") String comment) throws SeamanException, VesselException {
        recordService.addServiceRecordManually(seamanId, imoNumber, comment);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorMessage> seamanHandleException(SeamanException seamanException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(seamanException.getMessage()));
    }
    @ExceptionHandler
    public ResponseEntity<ErrorMessage> vesselHandleException(VesselException vesselException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(vesselException.getMessage()));
    }
}
