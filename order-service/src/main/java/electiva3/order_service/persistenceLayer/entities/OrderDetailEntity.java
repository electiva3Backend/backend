package electiva3.order_service.persistenceLayer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private BigDecimal unitaryCurrentPrice;

    @Column(name = "cantidad")
    private Integer quantity;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    // Opción Alternativa (Si obligatoriamente el parámetro debe seguir siendo Double)
    public OrderDetailEntity(BigDecimal unitaryCurrentPrice, Integer quantity) {
        this.unitaryCurrentPrice = unitaryCurrentPrice;
        this.quantity = quantity;

        // CORRECCIÓN: Convertir a BigDecimal por separado ANTES de multiplicar
        BigDecimal quantityBD = BigDecimal.valueOf(quantity);

        this.subtotal = unitaryCurrentPrice.multiply(quantityBD);
    }

}
