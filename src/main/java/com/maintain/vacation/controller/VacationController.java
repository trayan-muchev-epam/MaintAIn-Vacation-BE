package com.maintain.vacation.controller;
import com.maintain.vacation.dto.VacationRequest;
import com.maintain.vacation.dto.VacationResponse;
import com.maintain.vacation.service.VacationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/vacations")
public class VacationController {
    private final VacationService vacationService;
    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }
    @GetMapping
    public ResponseEntity<List<VacationResponse>> getAllVacations(Authentication authentication) {
        List<VacationResponse> vacations = vacationService.getAllVacations(authentication.getName());
        return ResponseEntity.ok(vacations);
    }
    @PostMapping
    public ResponseEntity<VacationResponse> createVacation(
            Authentication authentication,
            @Valid @RequestBody VacationRequest request) {
        VacationResponse response = vacationService.createVacation(authentication.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacation(
            Authentication authentication,
            @PathVariable String id) {
        vacationService.deleteVacation(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
