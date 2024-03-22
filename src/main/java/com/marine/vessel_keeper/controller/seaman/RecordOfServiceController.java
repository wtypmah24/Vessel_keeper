package com.marine.vessel_keeper.controller.seaman;

import com.marine.vessel_keeper.exception.SeamanException;
import com.marine.vessel_keeper.exception.VesselException;
import com.marine.vessel_keeper.service.seaman.RecordOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service_record")
public class RecordOfServiceController {
    private final RecordOfServiceService recordService;

    @Autowired
    public RecordOfServiceController(RecordOfServiceService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/add")
    public void addServiceRecordManually(@RequestParam("seamanId") long seamanId,
                                         @RequestParam("imoNumber") long imoNumber,
                                         @RequestParam("comment") String comment) throws SeamanException, VesselException {
        recordService.addServiceRecordManually(seamanId, imoNumber, comment);
    }
}
