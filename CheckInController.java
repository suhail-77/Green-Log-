package com.greenlog.greenlog.controller;

import com.greenlog.greenlog.entity.CheckIn;
import com.greenlog.greenlog.service.CheckInService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check-ins")
public class CheckInController {

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @GetMapping
    public List<CheckIn> getAllCheckIns() {
        return checkInService.getAllCheckIns();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckIn> getCheckInById(@PathVariable Long id) {

        return checkInService.getCheckInById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CheckIn> createCheckIn(
            @RequestBody CheckIn checkIn) {

        return ResponseEntity.ok(
                checkInService.saveCheckIn(checkIn)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CheckIn> updateCheckIn(
            @PathVariable Long id,
            @RequestBody CheckIn checkIn) {

        return checkInService.getCheckInById(id)
                .map(existingCheckIn -> {
                    existingCheckIn.setCheckInDate(checkIn.getCheckInDate());
                    existingCheckIn.setAlive(checkIn.getAlive());
                    existingCheckIn.setTree(checkIn.getTree());
                    existingCheckIn.setVolunteer(checkIn.getVolunteer());

                    return ResponseEntity.ok(
                            checkInService.saveCheckIn(existingCheckIn)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckIn(@PathVariable Long id) {

        if (checkInService.getCheckInById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        checkInService.deleteCheckIn(id);
        return ResponseEntity.noContent().build();
    }
}