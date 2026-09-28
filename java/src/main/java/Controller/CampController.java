package Controller;

import Entity.Camp;
import Services.CampService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/camps")
@RequiredArgsConstructor
public class CampController {

    private final CampService campService;

    @GetMapping
    public List<Camp> getAll() {
        return campService.findAll();
    }

    @GetMapping("/{id}")
    public Camp getById(@PathVariable Long id) {
        return campService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Camp create(@Valid @RequestBody Camp camp) {
        return campService.create(camp);
    }

    @PutMapping("/{id}")
    public Camp update(@PathVariable Long id,
                       @Valid @RequestBody Camp camp) {
        return campService.update(id, camp);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        campService.delete(id);
    }
}