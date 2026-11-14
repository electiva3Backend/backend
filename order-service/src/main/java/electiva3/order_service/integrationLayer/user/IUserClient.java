package electiva3.order_service.integrationLayer.user;

import electiva3.order_service.integrationLayer.user.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-service", url = "${user-service.url}")
public interface IUserClient {

    @GetMapping("/api/user/id/{id}")
    UserResponseDTO getUserById(@PathVariable Integer id);
}
