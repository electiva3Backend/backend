package electiva3.order_service.businessLayer.services.impl;

import electiva3.order_service.businessLayer.dto.OrderCreateDTO;
import electiva3.order_service.businessLayer.dto.OrderDetailCreateDTO;
import electiva3.order_service.businessLayer.dto.OrderResponseDTO;
import electiva3.order_service.businessLayer.services.IOrderService;
import electiva3.order_service.integrationLayer.product.IProductClient;
import electiva3.order_service.integrationLayer.product.dto.ProductResponseDTO;
import electiva3.order_service.integrationLayer.product.dto.ProductStockUpdateDTO;
import electiva3.order_service.integrationLayer.user.IUserClient;
import electiva3.order_service.integrationLayer.user.dto.UserResponseDTO;
import electiva3.order_service.persistenceLayer.dao.OrderDAO;
import electiva3.order_service.persistenceLayer.entities.OrderDetailEntity;
import electiva3.order_service.persistenceLayer.entities.OrderEntity;
import electiva3.order_service.persistenceLayer.enums.OrderStatus;
import electiva3.order_service.persistenceLayer.mappers.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final IProductClient productClient;
    private final IUserClient usertClient;
    private final OrderDAO orderDAO;

    @Transactional
    @Override
    public OrderResponseDTO create(OrderCreateDTO dto) {
        if (dto == null || dto.getIdUser() == null || dto.getOrderDetails() == null ||
                dto.getOrderDetails().isEmpty()) {
            throw new IllegalArgumentException("Invalid DATA");
        }

        UserResponseDTO user = usertClient.getUserById(dto.getIdUser());

        if (user == null) throw new RuntimeException("No existe el usuario con id: " + dto.getIdUser());

        // 1. Mapear la entidad padre (Order)
        OrderEntity orderEntity = OrderMapper.toEntity(dto);

        // 2. Extraer todos los IDs de productos del DTO
        List<Integer> idProducts = dto.getOrderDetails()
                .stream()
                .map(OrderDetailCreateDTO::getIdProduct)
                .toList();

        // 3. UNA SOLA LLAMADA HTTP: Obtener todos los productos desde el microservicio
        List<ProductResponseDTO> foundProds = productClient.getAllProductsById(idProducts);

        // Validar si faltó algún producto (por si pasaron un ID inexistente)
        if (foundProds.size() != idProducts.size()) {
            throw new RuntimeException("Uno o más productos no existen en el catálogo");
        }

        // 4. Convertir la lista de productos en un Map para búsquedas ultrarrápidas en memoria O(1)
        Map<Integer, ProductResponseDTO> mappedProds = foundProds
                .stream()
                .collect(Collectors.toMap(
                                ProductResponseDTO::id,
                                product -> product
                        )
                );

        List<ProductStockUpdateDTO> stockUpdates = new ArrayList<>();

        // 5. Procesar los detalles del DTO en un único bucle
        for (OrderDetailCreateDTO detailDto : dto.getOrderDetails()) {

            // Buscar el producto en nuestro mapa en memoria (cero llamadas HTTP aquí)
            ProductResponseDTO mappedProd = mappedProds.get(detailDto.getIdProduct());

            if (mappedProd == null) {
                throw new RuntimeException("El producto con ID " + detailDto.getIdProduct() + " no fue devuelto por el servicio");
            }

//            detailDto.setUnitaryCurrentPrice(mappedProd.price());

            // Validar el stock comparando lo que exige el DTO con lo que tiene el producto
            if (mappedProd.stock() < detailDto.getQuantity()) {
                throw new RuntimeException("No hay suficiente stock para el producto " + mappedProd.name());
            }

//            if (mappedProd.price().compareTo(detailDto.getUnitaryCurrentPrice()) != 0) {
//                throw new RuntimeException("Diferencia de precio en producto: " + mappedProd.name());
//            }

            // 6. Crear la entidad de detalle
            OrderDetailEntity newDetail = new OrderDetailEntity(
                    mappedProd.price(),
                    detailDto.getQuantity()
            );
            newDetail.setIdProduct(detailDto.getIdProduct());

            // 7. Asociar el detalle a la orden padre (esto acumula automáticamente tu total)
            orderEntity.addOrderDetail(newDetail);

            ProductStockUpdateDTO stockUpdate = new ProductStockUpdateDTO(
                    detailDto.getIdProduct(),
                    detailDto.getQuantity()
            );

            System.out.println(
                    "ORDER-SERVICE >>> ID: " + stockUpdate.id()
                            + " | QUANTITY: " + stockUpdate.quantity()
            );
            stockUpdates.add(stockUpdate);
        }
        orderEntity.setStatus(OrderStatus.CONFIRMED);

        // 8. Guardar todo en cascada
        OrderEntity savedEntity = orderDAO.save(orderEntity);

        // Se hace el update luego de guardar la orden para garantizar que no haya errores
        //Si descuento el stock, pero la orden falla, ya se hizo el descuento de una orden fallida
        productClient.reduceProductsStock(stockUpdates);

        return OrderMapper.toDTO(savedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponseDTO findById(Long id) {
        if (id == null) throw new RuntimeException("Id no existente");

        return orderDAO.findById(id)
                .map(OrderMapper::toDTO)
                .orElseThrow(() ->
                        new RuntimeException("No existe la orden")
                );
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> findAll() {
        List<OrderEntity> entities = orderDAO.findAll();

        return OrderMapper.toDTOList(entities);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> findAllByIdUser(Integer id) {
        List<OrderEntity> entities = orderDAO.findAllByIdUser(id);

        return OrderMapper.toDTOList(entities);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> findAllByStatus(String statusStr) {
        OrderStatus statusEnum = OrderStatus.valueOf(statusStr.toUpperCase().trim());

        List<OrderEntity> entities = orderDAO.findAllByStatus(statusEnum);

        return OrderMapper.toDTOList(entities);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> findAllByIdUserAndStatus(Integer id, String statusStr) {

        OrderStatus statusEnum = OrderStatus.valueOf(statusStr.toUpperCase().trim());

        List<OrderEntity> entities = orderDAO.findAllByIdUserAndStatus(id, statusEnum);

        return OrderMapper.toDTOList(entities);
    }

    @Transactional
    @Override
    public OrderResponseDTO cancel(Long id) {
        if (id == null) throw new RuntimeException("El Id es obligatorio");

        OrderEntity entity = orderDAO.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("No se encontró la orden")
                );

        List<Integer> productsIds = entity.getOrderDetails()
                .stream()
                .map(OrderDetailEntity::getIdProduct)
                .toList();

        List<ProductResponseDTO> foundProducts = productClient.getAllProductsById(productsIds);

        if (foundProducts.size() != productsIds.size()) {
            throw new RuntimeException("Uno o más productos no encontrados");
        }

//        List<ProductStockUpdateDTO> stockUpdates = new ArrayList<>();
//
//        for (OrderDetailEntity detailEntity : entity.getOrderDetails()) {
//
//            ProductStockUpdateDTO update = new ProductStockUpdateDTO(
//                    detailEntity.getIdProduct(),
//                    detailEntity.getQuantity()
//            );
//
//            stockUpdates.add(update);
//        }

        List<ProductStockUpdateDTO> stockUpdates = entity.getOrderDetails()
                .stream()
                .map(detailEntity -> new ProductStockUpdateDTO(
                                detailEntity.getIdProduct(),
                                detailEntity.getQuantity()
                        )
                ).toList();

        entity.setStatus(OrderStatus.CANCELLED);
        //Obliga a guardar en bd de inmediato sin almacenar en cache hasta finalizar el metodo
        //Si falla la bd, no se llama al productClient
        //Así se evita modificación de stock, per orden sin cancelar(fallo en bd)
        OrderEntity cancelledEntity = orderDAO.saveAndFlush(entity);

        productClient.increaseProductsStock(stockUpdates);

        return OrderMapper.toDTO(cancelledEntity);
    }
}
