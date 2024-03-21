package com.marine.vessel_keeper.controller.seaman;

import com.marine.vessel_keeper.dto.request.CertificateRequestDto;
import com.marine.vessel_keeper.dto.response.CertificateResponseDto;
import com.marine.vessel_keeper.service.seaman.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    private final CertificateService certificateService;
    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/addCertificate/{seamanId}")
    public ResponseEntity<Set<CertificateResponseDto>> addCertificateToSeaman(@RequestBody CertificateRequestDto certificateCandidate,
                                                                              @PathVariable long seamanId){
        return ResponseEntity.status(HttpStatus.CREATED).body(certificateService.addCertificateToSeaman(certificateCandidate, seamanId));
    }

    @DeleteMapping("/removeCertificate/{certificateId}")
    public ResponseEntity<Set<CertificateResponseDto>> removeCertificate(@PathVariable long certificateId){
        return ResponseEntity.status(HttpStatus.OK).body(certificateService.deleteCertificate(certificateId));
    }
}
