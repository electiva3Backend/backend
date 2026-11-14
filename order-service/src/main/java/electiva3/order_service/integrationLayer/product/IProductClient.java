package electiva3.order_service.integrationLayer.product;

import electiva3.order_service.integrationLayer.product.dto.ProductResponseDTO;
import electiva3.order_service.integrationLayer.product.dto.ProductStockUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product-service", url = "${product-service.url}")
public interface IProductClient {

    @GetMapping("/api/product/all/id")
    List<ProductResponseDTO> getAllProductsById(@RequestParam("ids") List<Integer> ids);

    @PutMapping("/api/product/all/r/ids")
    void reduceProductsStock(@RequestBody List<ProductStockUpdateDTO> prodsUpdate);

    @PutMapping("/api/product/all/i/ids")
    void increaseProductsStock(@RequestBody List<ProductStockUpdateDTO> prodsUpdate);
}
