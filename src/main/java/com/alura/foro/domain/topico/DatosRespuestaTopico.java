package com.alura.foro.domain.topico;

import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
    Long id,
    String titulo,
    String mensaje,
    LocalDateTime fechaCreacion,
    String status,
    DatosAutor autor,
    DatosCurso curso
) {
    public DatosRespuestaTopico(Topico topico) {
        this(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getFechaCreacion(),
            topico.getStatus().toString(),
            new DatosAutor(topico.getAutor()),
            new DatosCurso(topico.getCurso())
        );
    }
    
    public record DatosAutor(
        Long id,
        String nombre,
        String email
    ) {
        public DatosAutor(Usuario autor) {
            this(autor.getId(), autor.getNombre(), autor.getEmail());
        }
    }
    
    public record DatosCurso(
        Long id,
        String nombre,
        String categoria
    ) {
        public DatosCurso(Curso curso) {
            this(curso.getId(), curso.getNombre(), curso.getCategoria());
        }
    }
} 