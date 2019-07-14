package dev.jmvg.api.resource;

import dev.jmvg.api.event.EventoRecursoCriado;
import dev.jmvg.api.exceptionhandler.MyMoneyExceptionHandler;
import dev.jmvg.api.model.Lancamento;
import dev.jmvg.api.repository.LancamentoRepositorio;
import dev.jmvg.api.service.ServicoLancamento;
import dev.jmvg.api.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoRecurso {
    private final ServicoLancamento servicoLancamento;
    private final LancamentoRepositorio lancamentoRepositorio;
    private final MessageSource messageSource;
    private final ApplicationEventPublisher publisher;
    public LancamentoRecurso(ServicoLancamento servicoLancamento, LancamentoRepositorio lancamentoRepositorio, MessageSource messageSource, ApplicationEventPublisher publisher) {
        this.servicoLancamento = servicoLancamento;
        this.lancamentoRepositorio = lancamentoRepositorio;
        this.messageSource = messageSource;
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
        Lancamento lancamentoSalvo = servicoLancamento.salvar(lancamento);
        publisher.publishEvent(new EventoRecursoCriado(this, response, lancamentoSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex){
        String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<MyMoneyExceptionHandler.Erro> erros = Arrays.asList(new MyMoneyExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }

}
