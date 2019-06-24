package dev.jmvg.api.resource;


import dev.jmvg.api.model.Pessoa;
import dev.jmvg.api.repository.PessoaRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaRecurso {
    private final PessoaRepositorio pessoaRepositorio;

    public PessoaRecurso(PessoaRepositorio pessoaRepositorio) {
        this.pessoaRepositorio = pessoaRepositorio;
    }

    @RequestMapping
    public List<Pessoa> listarPessoas(){
        return pessoaRepositorio.findAll();
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse servletResponse){
        Pessoa pessoaSalva = pessoaRepositorio.save(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(pessoaSalva.getCodigo()).toUri();
        servletResponse.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(pessoaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> buscaPeloCodigo(@PathVariable Long codigo){
        Pessoa pessoa = pessoaRepositorio.findOne(codigo);
        return (pessoa != null) ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
    }
}
