package com.alura.foro.domain.topico;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DatosActualizarTopico(
    @NotNull
    Long id,
    
    @Size(max = 100)
    @Pattern(regexp = "^(?!\\s*$).+", message = "no puede estar vacío")
    String titulo,

    @Pattern(regexp = "^(?!\\s*$).+", message = "no puede estar vacío")
    String mensaje
) {} 