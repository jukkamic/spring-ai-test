package fi.kotkis.springai.controller;

import fi.kotkis.springai.domain.Material;
import fi.kotkis.springai.domain.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    @Autowired
    private MaterialRepository materialRepository;

    // GET endpoint to list all materials
    @GetMapping
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    // POST endpoint to add a new material
    @PostMapping
    public ResponseEntity<Material> createMaterial(@RequestBody Material material) {
        Material savedMaterial = materialRepository.save(material);
        return ResponseEntity.ok(savedMaterial);
    }
}