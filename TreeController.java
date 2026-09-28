package com.greenlog.greenlog.controller;

import com.greenlog.greenlog.entity.Tree;
import com.greenlog.greenlog.service.TreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trees")
public class TreeController {

    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping
    public List<Tree> getAllTrees() {
        return treeService.getAllTrees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tree> getTreeById(@PathVariable Long id) {

        return treeService.getTreeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tree> createTree(@RequestBody Tree tree) {

        return ResponseEntity.ok(
                treeService.saveTree(tree)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tree> updateTree(
            @PathVariable Long id,
            @RequestBody Tree tree) {

        return treeService.getTreeById(id)
                .map(existingTree -> {
                    existingTree.setSpecies(tree.getSpecies());
                    existingTree.setPlantedDate(tree.getPlantedDate());
                    existingTree.setLatitude(tree.getLatitude());
                    existingTree.setLongitude(tree.getLongitude());
                    existingTree.setStatus(tree.getStatus());
                    existingTree.setPlantationDrive(tree.getPlantationDrive());
                    existingTree.setVolunteer(tree.getVolunteer());

                    return ResponseEntity.ok(
                            treeService.saveTree(existingTree)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTree(@PathVariable Long id) {

        if (treeService.getTreeById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        treeService.deleteTree(id);
        return ResponseEntity.noContent().build();
    }
}