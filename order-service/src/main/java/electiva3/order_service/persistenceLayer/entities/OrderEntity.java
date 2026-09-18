package electiva3.order_service.persistenceLayer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
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
    private List<OrderDetailEntity> orderDetails;

    @Column(name = "id_user", nullable = false)
    private Integer idUSer;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "informacion", nullable = false)
    private String information;

    @Column(name = "total", nullable = false)
    private Double total;


    public void addOrderDetail(OrderDetailEntity orderDetail) {
        orderDetails.add(orderDetail);
        orderDetail.setOrder(this);

        this.total += orderDetail.getSubtotal();
    }

}
