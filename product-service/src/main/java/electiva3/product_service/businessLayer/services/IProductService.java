package electiva3.product_service.businessLayer.services;

import electiva3.product_service.businessLayer.dto.ProductCreateDTO;
import electiva3.product_service.businessLayer.dto.ProductResponseDTO;

import java.util.List;

public interface IProductService {

    ProductResponseDTO createProduct(ProductCreateDTO createDto);

    ProductResponseDTO getProductById(Integer id);

    ProductResponseDTO getProductByName(String name);

    List<ProductResponseDTO> getAllProducts();

    List<ProductResponseDTO> getAllProductsByCategory(String category);

    ProductResponseDTO updateProduct(Integer id, ProductCreateDTO createDto);

    void deleteProduct(Integer id);

}
