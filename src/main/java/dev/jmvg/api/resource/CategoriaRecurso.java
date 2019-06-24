package dev.jmvg.api.resource;

import dev.jmvg.api.event.EventoRecursoCriado;
import dev.jmvg.api.model.Categoria;
import dev.jmvg.api.repository.CategoriaRepositorio;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaRecurso {

    private final CategoriaRepositorio categoriaRepositorio;
    private final ApplicationEventPublisher publisher;
    public CategoriaRecurso(CategoriaRepositorio categoriaRepositorio, ApplicationEventPublisher publisher) {
        this.categoriaRepositorio = categoriaRepositorio;
        this.publisher = publisher;
    }

    @GetMapping
    public List<Categoria> listar(){
        return categoriaRepositorio.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
        Categoria categoriaSalva = categoriaRepositorio.save(categoria);
        publisher.publishEvent(new EventoRecursoCriado(this, response, categoriaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo){
        Categoria categoria = categoriaRepositorio.findOne(codigo);
        return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }
}
