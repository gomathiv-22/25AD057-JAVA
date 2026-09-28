package _AD057.java.Controller;

import _AD057.java.Entity.Distribution;
import _AD057.java.Services.DistributionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/distributions")
public class DistributionController {

    private final DistributionService distributionService;

    public DistributionController(DistributionService distributionService) {
        this.distributionService = distributionService;
    }

    // Get all distributions
    @GetMapping
    public List<Distribution> getAll() {
        return distributionService.findAll();
    }

    // Get distribution by ID
    @GetMapping("/{id}")
    public Distribution getById(@PathVariable Long id) {
        return distributionService.findById(id);
    }

    // Create distribution
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Distribution create(@Valid @RequestBody Distribution distribution) {
        return distributionService.create(distribution);
    }

    // Update distribution
    @PutMapping("/{id}")
    public Distribution update(@PathVariable Long id,
                               @Valid @RequestBody Distribution distribution) {
        return distributionService.update(id, distribution);
    }

    // Delete distribution
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        distributionService.delete(id);
    }
}