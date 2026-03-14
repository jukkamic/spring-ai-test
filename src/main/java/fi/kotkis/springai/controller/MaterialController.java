package fi.kotkis.springai.controller;

import fi.kotkis.springai.domain.Category;
import fi.kotkis.springai.domain.CategoryRepository;
import fi.kotkis.springai.domain.Material;
import fi.kotkis.springai.domain.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

    // POST endpoint to assign a category to a material
    @PostMapping("/{materialId}/category")
    public ResponseEntity<Material> assignCategoryToMaterial(
            @PathVariable Long materialId,
            @RequestBody Map<String, Long> categoryIdMap) {
        
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));
        
        Category category = categoryRepository.findById(categoryIdMap.get("categoryId"))
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        material.setCategory(category);
        Material updatedMaterial = materialRepository.save(material);
        
        return ResponseEntity.ok(updatedMaterial);
    }

    // GET endpoint to list all categories
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // POST endpoint to create a new category
    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }
}