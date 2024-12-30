package com.alura.foro.controller;

import com.alura.foro.domain.usuario.DatosAutenticacionUsuario;
import com.alura.foro.domain.usuario.Usuario;
import com.alura.foro.infra.security.DatosJWTToken;
import com.alura.foro.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticación", description = "Endpoints para autenticación de usuarios")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Operation(summary = "Autenticar usuario", description = "Autentica un usuario y retorna un token JWT")
    public ResponseEntity<DatosJWTToken> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datos) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.password());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

    @GetMapping("/test-password")
    @Operation(summary = "Probar hash de contraseña", description = "Genera un hash BCrypt para una contraseña dada")
    public ResponseEntity<String> testPassword(@RequestParam String password) {
        String hashedPassword = passwordEncoder.encode(password);
        return ResponseEntity.ok("Hash generado: " + hashedPassword);
    }

    @GetMapping("/verify-password")
    @Operation(summary = "Verificar hash de contraseña", description = "Verifica si un hash coincide con una contraseña")
    public ResponseEntity<String> verifyPassword(@RequestParam String password, @RequestParam String hash) {
        boolean matches = passwordEncoder.matches(password, hash);
        return ResponseEntity.ok("La contraseña " + (matches ? "coincide" : "no coincide") + " con el hash");
    }
} 