package electiva3.proyecto.persistenceLayer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "stock")
    private int stock;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "precio")
    private double price;

    @Column(name = "category")
    private String category;

}
