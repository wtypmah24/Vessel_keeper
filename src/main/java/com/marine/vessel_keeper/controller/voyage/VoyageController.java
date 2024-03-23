package com.marine.vessel_keeper.controller.voyage;

import com.marine.vessel_keeper.dto.request.VoyageRequestDto;
import com.marine.vessel_keeper.dto.response.VoyageResponseDto;
import com.marine.vessel_keeper.service.voyage.VoyageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<VoyageResponseDto> addVoyage(@RequestBody VoyageRequestDto voyageRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(voyageService.addNewVoyage(voyageRequestDto));
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<VoyageResponseDto>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(voyageService.getAllVoyages());
    }
}
