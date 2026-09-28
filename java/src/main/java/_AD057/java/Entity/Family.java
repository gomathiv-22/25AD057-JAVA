package _AD057.java.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "family")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long familyId;

    @NotBlank(message = "Family name is required")
    private String familyName;

    @Min(value = 1, message = "Headcount must be at least 1")
    private int headcount;

    private Long campId;
}