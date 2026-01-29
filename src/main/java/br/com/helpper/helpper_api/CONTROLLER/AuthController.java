/*
 * AuthController.java
 * Controller responsável pelos endpoints de autenticação
 * Gerencia login, registro e validação de disponibilidade de email
 * Endpoints públicos (não requerem autenticação)
 */
package br.com.helpper.helpper_api.CONTROLLER;

import br.com.helpper.helpper_api.DTO.LoginRequestDTO;
import br.com.helpper.helpper_api.DTO.LoginResponseDTO;
import br.com.helpper.helpper_api.DTO.RegisterRequestDTO;
import br.com.helpper.helpper_api.SERVICES.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // Configurar origens específicas em produção
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint de login
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        LoginResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint de registro
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> registrar(@Valid @RequestBody RegisterRequestDTO dto) {
        var response = authService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    /**
     * Verifica se email está disponível
     * GET /api/auth/check-email?email=teste@email.com
     */
    @GetMapping("/check-email")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String, Boolean>> verificarEmail(@RequestParam String email) {
        boolean disponivel = authService.emailDisponivel(email);

        Map<String, Boolean> response = new HashMap<>();
        response.put("disponivel", disponivel);

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint de health check
     * GET /api/auth/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("service", "Helpper API");

        return ResponseEntity.ok(response);
    }
}