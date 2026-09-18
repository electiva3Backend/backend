package electiva3.order_service.persistenceLayer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "detalle_orden")
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_orden")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orden", nullable = false)
    private OrderEntity order;

    @Column(name = "id_producto", nullable = false)
    private Integer idProduct;

    @Column(name = "precio_actual_unitario")
    private Double unitaryCurrentPrice;

    @Column(name = "cantidad")
    private Integer quantity;

    @Column(name = "subtotal")
    private Double subtotal;

    public OrderDetailEntity(Double unitaryCurrentPrice, Integer quantity) {
        this.unitaryCurrentPrice = unitaryCurrentPrice;
        this.quantity = quantity;

        this.subtotal = unitaryCurrentPrice * quantity;
    }
}
