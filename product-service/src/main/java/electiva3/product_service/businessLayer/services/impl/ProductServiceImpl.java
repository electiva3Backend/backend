package electiva3.product_service.businessLayer.services.impl;

import electiva3.product_service.businessLayer.dto.ProductCreateDTO;
import electiva3.product_service.businessLayer.dto.ProductResponseDTO;
import electiva3.product_service.businessLayer.dto.ProductStockUpdateDTO;
import electiva3.product_service.businessLayer.dto.ProductUpdateDTO;
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

    @Transactional
    @Override
    public ProductResponseDTO createProduct(ProductCreateDTO createDto) {
        normalizeDto(createDto);

        return productDAO.save(createDto);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponseDTO getProductById(Integer id) {
        if (id == null) throw new RuntimeException("El id es obligatorio");

        return productDAO.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("No se encontró el producto con id: " + id)
                );
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponseDTO getProductByName(String name) {
        if (name == null) throw new RuntimeException("El nombre es obligatorio");

        return productDAO.findByName(name)
                .orElseThrow(
                        () -> new RuntimeException("No se encontró el producto con nombre: " + name)
                );
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponseDTO> getAllProducts() {

        return productDAO.findAllProducts();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponseDTO> getAllProductsById(List<Integer> ids) {
        if (ids == null) throw new RuntimeException("Ids obligatorios");

        return productDAO.findAllProductsById(ids);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponseDTO> getAllProductsByCategory(String category) {
        if (category == null) throw new RuntimeException("La categoria es obligatoria");

        return productDAO.findAllProductsByCategory(category);
    }

    @Transactional
    @Override
    public ProductResponseDTO updateProduct(Integer id, ProductUpdateDTO updateDto) {
        if (id == null) throw new RuntimeException("El id es obligatorio");

        normalizeUpdateDto(updateDto);

        return productDAO.update(id, updateDto)
                .orElseThrow(() -> new RuntimeException("No se pudo actualizar"));
    }

    @Transactional
    @Override
    public void deleteProduct(Integer id) {
        if (id == null) throw new RuntimeException("El id es obligatorio");

        boolean deleted = productDAO.delete(id);

        if (!deleted) {
            throw new RuntimeException("No se pudo eliminar producto: " + id);
        }
    }

    @Transactional
    @Override
    public void reduceStockBulk(List<ProductStockUpdateDTO> stockUpdates) {

        if (stockUpdates == null) throw new RuntimeException("Lista vacía");

        stockUpdates.forEach(productDTO -> {
            System.out.println(
                    ">>> ID: " + productDTO.getId()
                            + " | QUANTITY: " + productDTO.getQuantity()
            );

            int rowsAffected = productDAO.reduceStockBulk(
                    productDTO.getId(),
                    productDTO.getQuantity()
            );

            System.out.println("FILAS AFECTADAS: " + rowsAffected);

            if (rowsAffected == 0) {
                throw new RuntimeException("No hay suficiente stock o no existe el prod");
            }
        });
    }

    @Transactional
    @Override
    public void increaseStockBulk(List<ProductStockUpdateDTO> stockUpdates) {

        if (stockUpdates == null) throw new RuntimeException("Lista vacía");

        for (ProductStockUpdateDTO updateDTO : stockUpdates) {
            int rowsAffected = productDAO.increaseStockBulk(
                    updateDTO.getId(),
                    updateDTO.getQuantity()
            );

            if (rowsAffected == 0) {
                throw new RuntimeException("No se actualizó el producto: " + updateDTO.getId());
            }
        }
    }

    private void normalizeDto(ProductCreateDTO dto) {
        dto.setName(dto.getName().trim().toLowerCase());
        dto.setCategory(dto.getCategory().trim().toLowerCase());
    }

    private void normalizeUpdateDto(ProductUpdateDTO dto) {
        dto.setName(dto.getName().trim().toLowerCase());
        dto.setCategory(dto.getCategory().trim().toLowerCase());
    }
}
