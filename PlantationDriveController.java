package com.greenlog.greenlog.controller;

import com.greenlog.greenlog.entity.PlantationDrive;
import com.greenlog.greenlog.service.PlantationDriveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantation-drives")
public class PlantationDriveController {

    private final PlantationDriveService plantationDriveService;

    public PlantationDriveController(PlantationDriveService plantationDriveService) {
        this.plantationDriveService = plantationDriveService;
    }

    @GetMapping
    public List<PlantationDrive> getAllPlantationDrives() {
        return plantationDriveService.getAllPlantationDrives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantationDrive> getPlantationDriveById(
            @PathVariable Long id) {

        return plantationDriveService.getPlantationDriveById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PlantationDrive> createPlantationDrive(
            @RequestBody PlantationDrive plantationDrive) {

        return ResponseEntity.ok(
                plantationDriveService.savePlantationDrive(plantationDrive)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantationDrive> updatePlantationDrive(
            @PathVariable Long id,
            @RequestBody PlantationDrive plantationDrive) {

        return plantationDriveService.getPlantationDriveById(id)
                .map(existingDrive -> {
                    existingDrive.setName(plantationDrive.getName());
                    existingDrive.setLocation(plantationDrive.getLocation());
                    existingDrive.setDriveDate(plantationDrive.getDriveDate());
                    existingDrive.setDescription(plantationDrive.getDescription());

                    return ResponseEntity.ok(
                            plantationDriveService.savePlantationDrive(existingDrive)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlantationDrive(
            @PathVariable Long id) {

        if (plantationDriveService.getPlantationDriveById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        plantationDriveService.deletePlantationDrive(id);
        return ResponseEntity.noContent().build();
    }
}