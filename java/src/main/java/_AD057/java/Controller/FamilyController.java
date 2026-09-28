package _AD057.java.Controller;

import _AD057.java.Entity.Family;
import _AD057.java.Services.FamilyService;
import jakarta.validation.Valid;
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

    @GetMapping
    public List<Family> getAll() {
        return familyService.findAll();
    }

    @GetMapping("/{id}")
    public Family getById(@PathVariable Long id) {
        return familyService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Family create(@Valid @RequestBody Family family) {
        return familyService.create(family);
    }

    @PutMapping("/{id}")
    public Family update(@PathVariable Long id,
                         @Valid @RequestBody Family family) {
        return familyService.update(id, family);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        familyService.delete(id);
    }
}