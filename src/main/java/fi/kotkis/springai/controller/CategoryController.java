package fi.kotkis.springai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.kotkis.springai.domain.Category;
import fi.kotkis.springai.domain.CategoryRepository;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/api/categories/{id}")
    public String updateCategory(
            @PathVariable Long id,
            @RequestParam String name,
            org.springframework.ui.Model model) {
        
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return "fragments/category-row-readonly :: row";
        }
        
        category.setName(name);
        categoryRepository.save(category);
        model.addAttribute("category", category);
        
        return "fragments/category-row-readonly :: row";
    }

    @GetMapping("/categories/{id}/edit")
    public String editCategory(@PathVariable Long id, org.springframework.ui.Model model) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return "fragments/category-row-readonly :: row";
        }
        model.addAttribute("category", category);
        return "fragments/category-row-edit :: row";
    }
}