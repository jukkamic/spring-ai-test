package fi.kotkis.springai.service;

import fi.kotkis.springai.domain.Material;
import fi.kotkis.springai.domain.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    /**
     * Find all materials where quantity is below their minimum threshold
     * @return List of low-stock materials
     */
    public List<Material> findLowStockMaterials() {
        return materialRepository.findByQuantityLessThanMinThreshold();
    }

    /**
     * Check if a material is low on stock
     * @param material The material to check
     * @return true if quantity < minThreshold, false otherwise
     */
    public boolean isLowStock(Material material) {
        if (material.getMinThreshold() == null) {
            return false;
        }
        return material.getQuantity() < material.getMinThreshold();
    }
}