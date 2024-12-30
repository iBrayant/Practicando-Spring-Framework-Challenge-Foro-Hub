package com.alura.foro.controller;

import com.alura.foro.domain.topico.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Tópicos", description = "Endpoints para gestión de tópicos del foro")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @PostMapping
    @Operation(summary = "Registrar tópico", description = "Registra un nuevo tópico en el foro")
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(
            @RequestBody @Valid DatosRegistroTopico datos,
            UriComponentsBuilder uriBuilder
    ) {
        var response = service.registrarTopico(datos);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar tópicos", description = "Lista todos los tópicos activos con paginación")
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(
            @PageableDefault(size = 10) Pageable paginacion,
            @RequestParam(required = false) String nombreCurso
    ) {
        var response = nombreCurso == null
                ? service.listarTopicos(paginacion)
                : service.listarTopicosPorCurso(nombreCurso, paginacion);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detallar tópico", description = "Obtiene los detalles de un tópico específico")
    public ResponseEntity<DatosRespuestaTopico> obtenerTopico(@PathVariable Long id) {
        var response = service.obtenerTopico(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @Operation(summary = "Actualizar tópico", description = "Actualiza la información de un tópico existente")
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(
            @RequestBody @Valid DatosActualizarTopico datos
    ) {
        var response = service.actualizarTopico(datos);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tópico", description = "Realiza un borrado lógico de un tópico")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        service.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
} 