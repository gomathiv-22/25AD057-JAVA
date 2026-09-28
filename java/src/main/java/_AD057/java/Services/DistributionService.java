package _AD057.java.Services;

import _AD057.java.Entity.Distribution;
import _AD057.java.Repository.DistributionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributionService {

    private final DistributionRepository distributionRepository;

    public DistributionService(DistributionRepository distributionRepository) {
        this.distributionRepository = distributionRepository;
    }

    // Get all distributions
    public List<Distribution> findAll() {
        return distributionRepository.findAll();
    }

    // Get distribution by ID
    public Distribution findById(Long id) {
        return distributionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Distribution not found with id: " + id));
    }

    // Create distribution
    public Distribution create(Distribution distribution) {
        return distributionRepository.save(distribution);
    }

    // Update distribution
    public Distribution update(Long id, Distribution distribution) {

        Distribution existingDistribution = distributionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Distribution not found with id: " + id));

        existingDistribution.setFamilyId(distribution.getFamilyId());
        existingDistribution.setSupplyId(distribution.getSupplyId());
        existingDistribution.setQuantity(distribution.getQuantity());

        return distributionRepository.save(existingDistribution);
    }

    // Delete distribution
    public void delete(Long id) {

        if (!distributionRepository.existsById(id)) {
            throw new RuntimeException(
                    "Distribution not found with id: " + id);
        }

        distributionRepository.deleteById(id);
    }
}