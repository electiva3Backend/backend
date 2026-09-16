package electiva3.product_service.persistenceLayer.repositories;

import electiva3.product_service.persistenceLayer.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IProductRepository extends JpaRepository<ProductEntity, Integer> {

    Optional<ProductEntity> findByName(String name);
    List<ProductEntity> findAllByCategory(String category);
}
