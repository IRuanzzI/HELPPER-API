/*
 * UsuarioController.java
 * Controller responsável por endpoints relacionados a Usuários
 * Gerencia perfil, atualização de dados e upload de foto
 * Endpoints protegidos - requer autenticação JWT
 */
package br.com.helpper.helpper_api.CONTROLLER;

import br.com.helpper.helpper_api.DTO.AlterarSenhaDTO;
import br.com.helpper.helpper_api.DTO.UsuarioDTO;
import br.com.helpper.helpper_api.DTO.UsuarioRequestDTO;
import br.com.helpper.helpper_api.SERVICES.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Busca perfil do usuário logado
     * GET /api/usuarios/me
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioDTO> buscarPerfil(Authentication authentication) {
        String email = authentication.getName();
        UsuarioDTO usuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Busca usuário por ID
     * GET /api/usuarios/{id}
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> userStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            Map<String, Object> response = new HashMap<>();
            response.put("loggedIn", true);
            response.put("username", auth.getName());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).build();
    }

    /**
     * Atualiza dados do usuário
     * PUT /api/usuarios/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto,
            Authentication authentication
    ) {
        // Verifica se usuário está alterando próprio perfil
        UsuarioDTO usuarioLogado = usuarioService.buscarPorEmail(authentication.getName());
        if (!usuarioLogado.getId().equals(id)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        UsuarioDTO atualizado = usuarioService.atualizarDadosComuns(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Altera senha do usuário
     * PATCH /api/usuarios/{id}/senha
     */
    @PatchMapping("/{id}/senha")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, String>> alterarSenha(
            @PathVariable Long id,
            @Valid @RequestBody AlterarSenhaDTO dto,
            Authentication authentication
    ) {
        // Verifica se usuário está alterando própria senha
        UsuarioDTO usuarioLogado = usuarioService.buscarPorEmail(authentication.getName());
        if (!usuarioLogado.getId().equals(id)) {
            return ResponseEntity.status(403).build();
        }

        usuarioService.alterarSenha(id, dto);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Senha alterada com sucesso");

        return ResponseEntity.ok(response);
    }

    /**
     * Upload de foto de perfil
     * POST /api/usuarios/{id}/foto
     */
    @PostMapping(value = "/{id}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioDTO> uploadFoto(
            @PathVariable Long id,
            @RequestParam("foto") MultipartFile foto,
            Authentication authentication
    ) throws IOException {
        // Verifica se usuário está alterando própria foto
        UsuarioDTO usuarioLogado = usuarioService.buscarPorEmail(authentication.getName());
        if (!usuarioLogado.getId().equals(id)) {
            return ResponseEntity.status(403).build();
        }

        // Valida tamanho do arquivo (max 5MB)
        if (foto.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("Arquivo muito grande. Máximo 5MB");
        }

        UsuarioDTO atualizado = usuarioService.atualizarFotoPerfil(id, foto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Deleta usuário
     * DELETE /api/usuarios/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, String>> deletar(
            @PathVariable Long id,
            Authentication authentication
    ) {
        // Verifica se usuário está deletando próprio perfil
        UsuarioDTO usuarioLogado = usuarioService.buscarPorEmail(authentication.getName());
        if (!usuarioLogado.getId().equals(id)) {
            return ResponseEntity.status(403).build();
        }

        usuarioService.deletarPorId(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário deletado com sucesso");

        return ResponseEntity.ok(response);
    }
}