package fi.kotkis.springai.controller;

import fi.kotkis.springai.domain.Category;
import fi.kotkis.springai.domain.CategoryRepository;
import fi.kotkis.springai.domain.Material;
import fi.kotkis.springai.domain.MaterialRepository;
import fi.kotkis.springai.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MaterialService materialService;

    @GetMapping("/")
    public String index(Model model) {
        List<Material> materials = materialRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        List<Material> lowStockMaterials = materialService.findLowStockMaterials();
        
        model.addAttribute("materials", materials);
        model.addAttribute("categories", categories);
        model.addAttribute("lowStockCount", lowStockMaterials.size());
        model.addAttribute("lowStockMaterials", lowStockMaterials);
        
        return "index";
    }

    @PostMapping("/materials/add")
    public String addMaterial(
            @RequestParam String name,
            @RequestParam Integer quantity,
            @RequestParam(required = false) Integer minThreshold,
            @RequestParam Long categoryId) {
        
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            Material material = new Material(name, quantity, minThreshold, false, null);
            material.setCategory(category);
            materialRepository.save(material);
        }
        
        return "redirect:/";
    }

    @PostMapping("/categories/add")
    public String addCategory(@RequestParam String name) {
        if (name != null && !name.trim().isEmpty()) {
            Category category = new Category(name.trim());
            categoryRepository.save(category);
        }
        return "redirect:/";
    }
}