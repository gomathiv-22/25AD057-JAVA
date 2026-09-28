package _AD057.java.Repository;

import _AD057.java.Entity.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DistributionRepository  extends JpaRepository<Distribution, Long>{
}

