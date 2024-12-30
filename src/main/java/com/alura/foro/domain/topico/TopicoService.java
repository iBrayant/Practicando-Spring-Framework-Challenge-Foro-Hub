package com.alura.foro.domain.topico;

import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.curso.CursoRepository;
import com.alura.foro.domain.usuario.Usuario;
import com.alura.foro.domain.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public DatosRespuestaTopico registrarTopico(DatosRegistroTopico datos) {
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new RuntimeException("Ya existe un tópico con el mismo título y mensaje");
        }

        Usuario autor = (Usuario) usuarioRepository.findById(datos.autorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor no encontrado"));

        Curso curso = cursoRepository.findById(datos.cursoId())
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));

        var topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);
        topicoRepository.save(topico);

        return new DatosRespuestaTopico(topico);
    }

    public Page<DatosListadoTopico> listarTopicos(Pageable paginacion) {
        return topicoRepository.findByActivoTrue(paginacion)
                .map(DatosListadoTopico::new);
    }

    public Page<DatosListadoTopico> listarTopicosPorCurso(String nombreCurso, Pageable paginacion) {
        return topicoRepository.findByCursoNombre(nombreCurso, paginacion)
                .map(DatosListadoTopico::new);
    }

    public DatosRespuestaTopico obtenerTopico(Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));
        return new DatosRespuestaTopico(topico);
    }

    @Transactional
    public DatosRespuestaTopico actualizarTopico(DatosActualizarTopico datos) {
        var topico = topicoRepository.findById(datos.id())
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        topico.actualizar(datos.titulo(), datos.mensaje());

        return new DatosRespuestaTopico(topico);
    }

    @Transactional
    public void eliminarTopico(Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));
        
        topico.desactivar();
    }
} 