package electiva3.order_service.persistenceLayer.entities;

import electiva3.order_service.persistenceLayer.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "orden")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetailEntity> orderDetails = new ArrayList<>();

    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "informacion", nullable = false)
    private String information;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private OrderStatus status;

    //Debe inicializarse ya que hago operaciones con el dato. Si no se inicializa hay nullpointer
    @Column(name = "total", nullable = false)
    private BigDecimal total = BigDecimal.valueOf(0);

    public void addOrderDetail(OrderDetailEntity orderDetail) {
        if (orderDetail == null || orderDetail.getSubtotal() == null) {
            throw new IllegalArgumentException("El detalle de orden o su subtotal no pueden ser nulos");
        }

        orderDetails.add(orderDetail);
        orderDetail.setOrder(this);

        // CORRECCIÓN: Sumar usando el método .add() de BigDecimal y reasignar
        this.total = this.total.add(orderDetail.getSubtotal());
    }

}
