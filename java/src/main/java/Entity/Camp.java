package Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "camp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Camp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long campId;

    @NotBlank(message = "Camp name is required")
    private String campName;

    @NotBlank(message = "Location is required")
    private String location;

    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;
}