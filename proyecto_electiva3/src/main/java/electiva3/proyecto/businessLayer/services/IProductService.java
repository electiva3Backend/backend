package electiva3.proyecto.businessLayer.services;

import electiva3.proyecto.businessLayer.dto.ProductCreateDTO;
import electiva3.proyecto.businessLayer.dto.ProductResponseDTO;
import org.springframework.stereotype.Service;

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
