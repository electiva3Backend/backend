package electiva3.proyecto.presentationLayer.controllers;

import electiva3.proyecto.businessLayer.dto.ProductCreateDTO;
import electiva3.proyecto.businessLayer.dto.ProductResponseDTO;
import electiva3.proyecto.businessLayer.services.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDTO>> getAllProductsByCategory(
            @PathVariable String category) {

        return ResponseEntity.ok(prodServ.getAllProductsByCategory(category));
    }

    @PutMapping("id/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody ProductCreateDTO dto) {

        return ResponseEntity.ok(prodServ.updateProduct(id, dto));
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Integer id) {

        prodServ.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
