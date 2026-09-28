package _AD057.java.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supply")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplyId;

    @NotBlank(message = "Supply type is required")
    private String supplyType;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    private Long campId;
}