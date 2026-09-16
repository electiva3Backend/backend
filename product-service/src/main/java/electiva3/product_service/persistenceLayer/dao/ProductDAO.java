package electiva3.product_service.persistenceLayer.dao;

import electiva3.product_service.businessLayer.dto.ProductCreateDTO;
import electiva3.product_service.businessLayer.dto.ProductResponseDTO;
import electiva3.product_service.persistenceLayer.entities.ProductEntity;
import electiva3.product_service.persistenceLayer.mappers.ProductMapper;
import electiva3.product_service.persistenceLayer.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductDAO {

    private final IProductRepository productRepository;

    public ProductResponseDTO save(ProductCreateDTO createDto) {

        ProductEntity entity = ProductMapper.toEntity(createDto);
        ProductEntity savedEntity = productRepository.save(entity);

        return ProductMapper.toResponseDTO(savedEntity);
    }

    public Optional<ProductResponseDTO> findById(Integer id) {

        return productRepository.findById(id)
                .map(ProductMapper::toResponseDTO);
    }

    public Optional<ProductResponseDTO> findByName(String name) {

        return productRepository.findByName(name)
                .map(ProductMapper::toResponseDTO);
    }

    public List<ProductResponseDTO> findAllProducts() {

        List<ProductEntity> entities = productRepository.findAll();
        return ProductMapper.toDTOList(entities);
    }

    public List<ProductResponseDTO> findAllProductsByCategory(String category) {

        List<ProductEntity> entities = productRepository.findAllByCategory(category);
        return ProductMapper.toDTOList(entities);
    }

    public Optional<ProductResponseDTO> update(Integer id, ProductCreateDTO updateDto) {

        return productRepository.findById(id)
                .map(existingEntity -> {
                    ProductMapper.updateEntityFromDTO(
                            existingEntity,
                            updateDto
                    );
                    productRepository.save(existingEntity);
                    return ProductMapper.toResponseDTO(existingEntity);
                });
    }

    public boolean delete(Integer id) {

        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
