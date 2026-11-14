package electiva3.product_service.persistenceLayer.repositories;

import electiva3.product_service.persistenceLayer.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IProductRepository extends JpaRepository<ProductEntity, Integer> {

    Optional<ProductEntity> findByName(String name);

    List<ProductEntity> findAllByCategory(String category);

    @Modifying
    @Transactional
    @Query("""
                UPDATE ProductEntity p
                SET p.stock = p.stock - :quantity
                WHERE p.id = :id
                  AND p.stock >= :quantity
            """)
    int decreaseStock(
            @Param("id") Integer id,
            @Param("quantity") int quantity
    );

    @Modifying
    @Transactional
    @Query("""
                UPDATE ProductEntity p
                SET p.stock = p.stock + :quantity
                WHERE p.id = :id
            """)
    int increaseStock(
            @Param("id") Integer id,
            @Param("quantity") int quantity
    );
}
