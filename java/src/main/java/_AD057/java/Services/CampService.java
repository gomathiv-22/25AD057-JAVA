package _AD057.java.Services;

import _AD057.java.Entity.Camp;
import _AD057.java.Repository.CampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampService {

    private final CampRepository campRepository;

    // Get all camps
    public List<Camp> findAll() {
        return campRepository.findAll();
    }

    // Get camp by ID
    public Camp findById(Long id) {
        return campRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Camp not found with id: " + id));
    }

    // Create camp
    public Camp create(Camp camp) {
        return campRepository.save(camp);
    }

    // Update camp
    public Camp update(Long id, Camp camp) {

        Camp existingCamp = campRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Camp not found with id: " + id));

        existingCamp.setCampName(camp.getCampName());
        existingCamp.setLocation(camp.getLocation());
        existingCamp.setCapacity(camp.getCapacity());

        return campRepository.save(existingCamp);
    }

    // Delete camp
    public void delete(Long id) {

        if (!campRepository.existsById(id)) {
            throw new RuntimeException(
                    "Camp not found with id: " + id);
        }

        campRepository.deleteById(id);
    }
}