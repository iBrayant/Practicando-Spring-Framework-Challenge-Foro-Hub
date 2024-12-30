package com.alura.foro.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    
    Page<Topico> findByActivoTrue(Pageable paginacion);
    
    @Query("""
            SELECT t FROM Topico t
            WHERE t.activo = true
            AND t.curso.nombre = :nombreCurso
            """)
    Page<Topico> findByCursoNombre(String nombreCurso, Pageable paginacion);
    
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
} 