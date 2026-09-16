package electiva3.product_service.businessLayer.services.impl;

import electiva3.product_service.businessLayer.dto.ProductCreateDTO;
import electiva3.product_service.businessLayer.dto.ProductResponseDTO;
import electiva3.product_service.businessLayer.services.IProductService;

import electiva3.product_service.persistenceLayer.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductDAO productDAO;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductCreateDTO createDto) {
        normalizeDto(createDto);

        return productDAO.save(createDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Integer id) {
        if (id == null) throw new RuntimeException("El id es obligatorio");

        return productDAO.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("No se encontró el producto con id: " + id)
                );
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductByName(String name) {
        if (name == null) throw new RuntimeException("El nombre es obligatorio");

        return productDAO.findByName(name)
                .orElseThrow(
                        () -> new RuntimeException("No se encontró el producto con nombre: " + name)
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {

        return productDAO.findAllProducts();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProductsByCategory(String category) {
        if (category == null) throw new RuntimeException("La categoria es obligatoria");

        return productDAO.findAllProductsByCategory(category);
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Integer id, ProductCreateDTO updateDto) {
        if (id == null) throw new RuntimeException("El id es obligatorio");

        normalizeDto(updateDto);

        return productDAO.update(id, updateDto)
                .orElseThrow(() -> new RuntimeException("No se pudo actualizar"));
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        if (id == null) throw new RuntimeException("El id es obligatorio");

        boolean deleted = productDAO.delete(id);

        if (!deleted) {
            throw new RuntimeException("No se pudo eliminar producto: " + id);
        }
    }

    private void normalizeDto(ProductCreateDTO dto) {
        dto.setName(dto.getName().trim().toLowerCase());
        dto.setCategory(dto.getCategory().trim().toLowerCase());
    }

}
