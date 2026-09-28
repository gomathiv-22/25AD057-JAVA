package _AD057.java.Services;

import _AD057.java.Entity.Supply;
import _AD057.java.Repository.SupplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyService {

    private final SupplyRepository supplyRepository;

    public SupplyService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    public List<Supply> findAll() {
        return supplyRepository.findAll();
    }

    public Supply findById(Long id) {
        return supplyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Supply not found with id: " + id));
    }

    public Supply create(Supply supply) {
        return supplyRepository.save(supply);
    }

    public Supply update(Long id, Supply supply) {

        Supply existingSupply = supplyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Supply not found with id: " + id));

        existingSupply.setSupplyType(supply.getSupplyType());
        existingSupply.setQuantity(supply.getQuantity());
        existingSupply.setCampId(supply.getCampId());

        return supplyRepository.save(existingSupply);
    }

    public void delete(Long id) {

        if (!supplyRepository.existsById(id)) {
            throw new RuntimeException(
                    "Supply not found with id: " + id);
        }

        supplyRepository.deleteById(id);
    }
}