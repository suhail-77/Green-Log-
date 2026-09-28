package com.greenlog.greenlog.controller;

import com.greenlog.greenlog.entity.Volunteer;
import com.greenlog.greenlog.service.VolunteerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping
    public List<Volunteer> getAllVolunteers() {
        return volunteerService.getAllVolunteers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Long id) {
        return volunteerService.getVolunteerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(
            @RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.saveVolunteer(volunteer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteer(
            @PathVariable Long id,
            @RequestBody Volunteer volunteer) {

        return volunteerService.getVolunteerById(id)
                .map(existingVolunteer -> {
                    existingVolunteer.setName(volunteer.getName());
                    existingVolunteer.setEmail(volunteer.getEmail());
                    existingVolunteer.setPhone(volunteer.getPhone());
                    return ResponseEntity.ok(
                            volunteerService.saveVolunteer(existingVolunteer)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
        if (volunteerService.getVolunteerById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        volunteerService.deleteVolunteer(id);
        return ResponseEntity.noContent().build();
    }
}