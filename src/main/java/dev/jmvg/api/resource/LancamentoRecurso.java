package dev.jmvg.api.resource;

import dev.jmvg.api.event.EventoRecursoCriado;
import dev.jmvg.api.model.Lancamento;
import dev.jmvg.api.repository.LancamentoRepositorio;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoRecurso {
    private final LancamentoRepositorio lancamentoRepositorio;
    private final ApplicationEventPublisher publisher;
    public LancamentoRecurso(LancamentoRepositorio lancamentoRepositorio, ApplicationEventPublisher publisher) {
        this.lancamentoRepositorio = lancamentoRepositorio;
        this.publisher = publisher;
    }

    @GetMapping
    public List<Lancamento> listar(){return lancamentoRepositorio.findAll(); }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo){
        Lancamento lancamento = lancamentoRepositorio.findOne(codigo);
        return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
        Lancamento lancamentoSalvo = lancamentoRepositorio.save(lancamento);
        publisher.publishEvent(new EventoRecursoCriado(this, response, lancamentoSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }
}
