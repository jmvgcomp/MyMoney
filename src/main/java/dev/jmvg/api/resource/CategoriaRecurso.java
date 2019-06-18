package dev.jmvg.api.resource;

import dev.jmvg.api.model.Categoria;
import dev.jmvg.api.repository.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaRecurso {

    private final CategoriaRepositorio categoriaRepositorio;

    public CategoriaRecurso(CategoriaRepositorio categoriaRepositorio) {
        this.categoriaRepositorio = categoriaRepositorio;
    }

    @GetMapping
    public List<Categoria> listar(){
        return categoriaRepositorio.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(@RequestBody Categoria categoria){
        categoriaRepositorio.save(categoria);

    }
}
