package fi.kotkis.springai.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    
    @Query("SELECT m FROM Material m WHERE m.minThreshold IS NOT NULL AND m.quantity < m.minThreshold")
    List<Material> findByQuantityLessThanMinThreshold();
}
