package fi.kotkis.springai.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Boolean isDelivered;

    private LocalDate expiryDate;

    // Default constructor required by JPA
    public Material() {
    }

    // Constructor with fields
    public Material(String name, Integer quantity, Boolean isDelivered) {
        this.name = name;
        this.quantity = quantity;
        this.isDelivered = isDelivered;
    }

    // Constructor with fields including expiryDate
    public Material(String name, Integer quantity, Boolean isDelivered, LocalDate expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.isDelivered = isDelivered;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
