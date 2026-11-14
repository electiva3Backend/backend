package electiva3.product_service.presentationLayer.controllers;

import electiva3.product_service.businessLayer.dto.ProductStockUpdateDTO;
import electiva3.product_service.businessLayer.dto.ProductCreateDTO;
import electiva3.product_service.businessLayer.dto.ProductResponseDTO;
import electiva3.product_service.businessLayer.dto.ProductUpdateDTO;
import electiva3.product_service.businessLayer.services.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService prodServ;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductCreateDTO dto) {

        ProductResponseDTO created = prodServ.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(prodServ.getProductById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductResponseDTO> getProductByName(
            @PathVariable String name) {

        return ResponseEntity.ok(prodServ.getProductByName(name));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {

        return ResponseEntity.ok(prodServ.getAllProducts());
    }

    @GetMapping("/all/id")
    public ResponseEntity<List<ProductResponseDTO>> getAllProductsById(
            @RequestParam("ids") List<Integer> ids
    ) {

        return ResponseEntity.ok(prodServ.getAllProductsById(ids));
    }

    @PutMapping("all/r/ids")
    public ResponseEntity<Void> reduceProductsStock(
            @RequestBody List<ProductStockUpdateDTO> quantityUpdate
    ) {
        prodServ.reduceStockBulk(quantityUpdate);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("all/i/ids")
    public ResponseEntity<Void> increaseProductsStock(
            @RequestBody List<ProductStockUpdateDTO> quantityUpdate
    ) {
        prodServ.increaseStockBulk(quantityUpdate);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDTO>> getAllProductsByCategory(
            @PathVariable String category) {

        return ResponseEntity.ok(prodServ.getAllProductsByCategory(category));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody ProductUpdateDTO dto) {

        return ResponseEntity.ok(prodServ.updateProduct(id, dto));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Integer id) {

        prodServ.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
