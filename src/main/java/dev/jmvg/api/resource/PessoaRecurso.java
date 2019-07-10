package dev.jmvg.api.resource;


import dev.jmvg.api.event.EventoRecursoCriado;
import dev.jmvg.api.model.Pessoa;
import dev.jmvg.api.repository.PessoaRepositorio;
import dev.jmvg.api.service.ServicoPessoa;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaRecurso {
    private final PessoaRepositorio pessoaRepositorio;
    private ServicoPessoa servicoPessoa;
    public PessoaRecurso(PessoaRepositorio pessoaRepositorio, ServicoPessoa servicoPessoa, ApplicationEventPublisher publisher) {
        this.pessoaRepositorio = pessoaRepositorio;
        this.servicoPessoa = servicoPessoa;
        this.publisher = publisher;
    }

    private final ApplicationEventPublisher publisher;

    @RequestMapping
    public List<Pessoa> listarPessoas(){
        return pessoaRepositorio.findAll();
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse servletResponse){
        Pessoa pessoaSalva = pessoaRepositorio.save(pessoa);
        publisher.publishEvent(new EventoRecursoCriado(this, servletResponse, pessoaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> buscaPeloCodigo(@PathVariable Long codigo){
        Pessoa pessoa = pessoaRepositorio.findOne(codigo);
        return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo){
        pessoaRepositorio.delete(codigo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
        Pessoa pessoaSalva = servicoPessoa.pessoaAtualizar(codigo, pessoa);
        return ResponseEntity.ok(pessoaSalva);
    }

    @PutMapping("/{codigo}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
        servicoPessoa.atualizarPropriedadeAtivo(codigo, ativo);
    }
}
