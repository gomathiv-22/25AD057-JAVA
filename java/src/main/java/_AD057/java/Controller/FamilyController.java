package Controller;

import Entity.Family;
import Services.FamilyService;
import _AD057.java.Entity.Family;
import jakarta.validation.Valid;
import org.hibernate.type.descriptor.jdbc.JdbcTypeFamilyInformation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/families")
public class FamilyController {

    private final FamilyService familyService;

    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    // Get all families
    @GetMapping
    public List<_AD057.java.Entity.Family> getAll() {
        return familyService.findAll();
    }

    // Get family by ID
    @GetMapping("/{id}")
    public Family getById(@PathVariable Long id) {
        return familyService.findById(id);
    }

    // Create family
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Family create(@Valid @RequestBody Family family) {
        return familyService.create(family);
    }

    // Update family
    @PutMapping("/{id}")
    public Family update(@PathVariable Long id,
                         @Valid @RequestBody Family family) {
        return familyService.update(id, family);
    }

    // Delete family
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        familyService.delete(id);
    }
}