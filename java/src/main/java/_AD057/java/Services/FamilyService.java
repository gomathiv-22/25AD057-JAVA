package _AD057.java.Services;

import _AD057.java.Entity.Family;
import _AD057.java.Repository.FamilyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyService {

    private final FamilyRepository familyRepository;

    public FamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    public List<Family> findAll() {
        return familyRepository.findAll();
    }

    public Family findById(Long id) {
        return familyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Family not found with id: " + id));
    }

    public Family create(Family family) {
        return familyRepository.save(family);
    }

    public Family update(Long id, Family family) {

        Family existingFamily = familyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Family not found with id: " + id));

        existingFamily.setFamilyName(family.getFamilyName());
        existingFamily.setHeadcount(family.getHeadcount());
        existingFamily.setCampId(family.getCampId());

        return familyRepository.save(existingFamily);
    }

    public void delete(Long id) {

        if (!familyRepository.existsById(id)) {
            throw new RuntimeException("Family not found with id: " + id);
        }

        familyRepository.deleteById(id);
    }
}