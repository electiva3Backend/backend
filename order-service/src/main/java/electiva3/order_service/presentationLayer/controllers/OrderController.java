package electiva3.order_service.presentationLayer.controllers;

import electiva3.order_service.businessLayer.dto.OrderCreateDTO;
import electiva3.order_service.businessLayer.dto.OrderResponseDTO;
import electiva3.order_service.businessLayer.services.IOrderService;
import electiva3.order_service.persistenceLayer.enums.OrderStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final IOrderService orderService;

    @GetMapping("id/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {

        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {

        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("userid/{id}")
    public ResponseEntity<List<OrderResponseDTO>> findAllByIdUser(@PathVariable Integer id) {

        return ResponseEntity.ok(orderService.findAllByIdUser(id));
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<OrderResponseDTO>> findAllByStatus(@PathVariable String status) {

        return ResponseEntity.ok(orderService.findAllByStatus(status));
    }

    @GetMapping("userid/{id}/status/{status}")
    public ResponseEntity<List<OrderResponseDTO>> findAllByIdAndStatus(
            @PathVariable Integer id,
            @PathVariable String status
    ) {

        return ResponseEntity.ok(orderService.findAllByIdUserAndStatus(id, status));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody OrderCreateDTO dto) {

        OrderResponseDTO created = orderService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PutMapping("id/{id}")
    public ResponseEntity<OrderResponseDTO> cancel(@PathVariable Long id) {

        return ResponseEntity.ok(orderService.cancel(id));
    }

}
