package com.alura.foro.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
    @NotBlank
    @Size(max = 100)
    String titulo,
    
    @NotBlank
    String mensaje,
    
    @NotNull
    Long autorId,
    
    @NotNull
    Long cursoId
) {} 