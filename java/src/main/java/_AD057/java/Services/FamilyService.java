package Services;

import Entity.Family;
import Repository.FamilyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyService {

    private final FamilyRepository familyRepository;

    public FamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    // Get all families
    public List<Family> findAll() {
        return familyRepository.findAll();
    }

    // Get family by ID
    public Family findById(Long id) {
        return familyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Family not found with id: " + id));
    }

    // Create family
    public Family create(Family family) {
        return familyRepository.save(family);
    }

    // Update family
    public Family update(Long id, Family family) {

        Family existingFamily = familyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Family not found with id: " + id));

        existingFamily.setFamilyName(family.getFamilyName());
        existingFamily.setHeadcount(family.getHeadcount());
        existingFamily.setCampId(family.getCampId());

        return familyRepository.save(existingFamily);
    }

    // Delete family
    public void delete(Long id) {

        if (!familyRepository.existsById(id)) {
            throw new RuntimeException(
                    "Family not found with id: " + id);
        }

        familyRepository.deleteById(id);
    }
}