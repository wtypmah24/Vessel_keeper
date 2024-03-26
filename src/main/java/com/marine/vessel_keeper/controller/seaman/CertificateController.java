package com.marine.vessel_keeper.controller.seaman;

import com.marine.vessel_keeper.dto.request.CertificateRequestDto;
import com.marine.vessel_keeper.dto.response.CertificateResponseDto;
import com.marine.vessel_keeper.exception.SeamanCertificateException;
import com.marine.vessel_keeper.exception.SeamanException;
import com.marine.vessel_keeper.service.seaman.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/certificates")
@Tag(name = "Seaman's certificates controller.",
        description = "Here you can add or remove seaman's certificate.")
public class CertificateController {
    private final CertificateService certificateService;
    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/addCertificate/{seamanId}")
    @Operation(
            summary = "Add certificate to a seaman",
            description = "Allows you add a certificate to a seaman. You can't add a certificate without connecting it to a seaman."
    )
    public ResponseEntity<Set<CertificateResponseDto>> addCertificateToSeaman(@RequestBody CertificateRequestDto certificateCandidate,
                                                                              @PathVariable long seamanId) throws SeamanException, SeamanCertificateException {
        return ResponseEntity.status(HttpStatus.CREATED).body(certificateService.addCertificateToSeaman(certificateCandidate, seamanId));
    }
    @Operation(summary = "Remove seaman's certificate")
    @DeleteMapping("/removeCertificate/{certificateId}")
    public ResponseEntity<Set<CertificateResponseDto>> removeCertificate(@PathVariable long certificateId) throws SeamanCertificateException {
        return ResponseEntity.status(HttpStatus.OK).body(certificateService.deleteCertificate(certificateId));
    }
}
