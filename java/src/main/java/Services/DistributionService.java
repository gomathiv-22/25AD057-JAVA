package Services;

import Entity.Distribution;
import Entity.Supply;
import Repository.DistributionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistributionService {

    private final DistributionRepository distributionRepository;

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
    @Transactional
    public Distribution create(Distribution distribution) {

        Supply supply = distribution.getSupply();

        if (distribution.getQuantity() > supply.getQuantity()) {
            throw new RuntimeException(
                    "Insufficient supply. Available quantity: "
                            + supply.getQuantity());
        }

        // Reduce supply quantity
        supply.setQuantity(
                supply.getQuantity() - distribution.getQuantity()
        );

        return distributionRepository.save(distribution);
    }

    // Update distribution
    @Transactional
    public Distribution update(Long id, Distribution distribution) {

        Distribution existingDistribution = distributionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Distribution not found with id: " + id));

        Supply oldSupply = existingDistribution.getSupply();

        // Return old distributed quantity to inventory
        oldSupply.setQuantity(
                oldSupply.getQuantity() + existingDistribution.getQuantity()
        );

        Supply newSupply = distribution.getSupply();

        // Check new quantity
        if (distribution.getQuantity() > newSupply.getQuantity()) {
            throw new RuntimeException(
                    "Insufficient supply. Available quantity: "
                            + newSupply.getQuantity());
        }

        // Reduce new supply quantity
        newSupply.setQuantity(
                newSupply.getQuantity() - distribution.getQuantity()
        );

        existingDistribution.setQuantity(distribution.getQuantity());
        existingDistribution.setFamily(distribution.getFamily());
        existingDistribution.setSupply(distribution.getSupply());

        return distributionRepository.save(existingDistribution);
    }

    // Delete distribution
    @Transactional
    public void delete(Long id) {

        Distribution existingDistribution = distributionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Distribution not found with id: " + id));

        // Return distributed quantity to inventory
        Supply supply = existingDistribution.getSupply();

        supply.setQuantity(
                supply.getQuantity() + existingDistribution.getQuantity()
        );

        distributionRepository.deleteById(id);
    }
}