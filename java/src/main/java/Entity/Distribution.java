package Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "distribution")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Distribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long distributionId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "family_id", nullable = false)
    private Family family;

    @ManyToOne
    @JoinColumn(name = "supply_id", nullable = false)
    private Supply supply;
}