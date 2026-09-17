package electiva3.user_service.presentationLayer.controllers;

import electiva3.user_service.businessLayer.dto.LoginRequestDTO;
import electiva3.user_service.businessLayer.dto.UserRegisterDTO;
import electiva3.user_service.businessLayer.dto.UserResponseDTO;
import electiva3.user_service.businessLayer.dto.UserUpdateDTO;
import electiva3.user_service.businessLayer.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRegisterDTO dto
    ) {
        UserResponseDTO userCreated = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDTO> findByid(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserUpdateDTO dto
    ) {
        return ResponseEntity.ok(userService.update(dto, id));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<UserResponseDTO> delete(
            @PathVariable Integer id
    ) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(
                userService.login(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
    }

}
