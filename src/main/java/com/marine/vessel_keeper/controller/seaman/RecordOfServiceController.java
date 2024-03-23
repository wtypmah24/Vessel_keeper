package com.marine.vessel_keeper.controller.seaman;

import com.marine.vessel_keeper.exception.SeamanException;
import com.marine.vessel_keeper.exception.VesselException;
import com.marine.vessel_keeper.service.seaman.RecordOfServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service_record")
@Tag(name = "Seaman's record of services controller.",
        description = "Here you can add manually working experience to a seaman.")
public class RecordOfServiceController {
    private final RecordOfServiceService recordService;

    @Autowired
    public RecordOfServiceController(RecordOfServiceService recordService) {
        this.recordService = recordService;
    }

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
}
