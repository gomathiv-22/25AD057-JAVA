package _AD057.java.Controller;

import _AD057.java.Entity.Supply;
import _AD057.java.Services.SupplyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplies")
public class SupplyController {

    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @GetMapping
    public List<Supply> getAll() {
        return supplyService.findAll();
    }

    @GetMapping("/{id}")
    public Supply getById(@PathVariable Long id) {
        return supplyService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Supply create(@Valid @RequestBody Supply supply) {
        return supplyService.create(supply);
    }

    @PutMapping("/{id}")
    public Supply update(@PathVariable Long id,
                         @Valid @RequestBody Supply supply) {
        return supplyService.update(id, supply);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        supplyService.delete(id);
    }
}