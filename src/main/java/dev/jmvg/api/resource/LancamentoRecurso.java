package dev.jmvg.api.resource;

import dev.jmvg.api.event.EventoRecursoCriado;
import dev.jmvg.api.exceptionhandler.MyMoneyExceptionHandler;
import dev.jmvg.api.model.Lancamento;
import dev.jmvg.api.repository.LancamentoRepository;
import dev.jmvg.api.repository.filtro.LancamentoFiltro;
import dev.jmvg.api.repository.projection.ResumoLancamento;
import dev.jmvg.api.service.ServicoLancamento;
import dev.jmvg.api.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoRecurso {
    private final ServicoLancamento servicoLancamento;
    private final LancamentoRepository lancamentoRepository;
    private final MessageSource messageSource;
    private final ApplicationEventPublisher publisher;
    @Autowired
    public LancamentoRecurso(ServicoLancamento servicoLancamento, LancamentoRepository lancamentoRepository, MessageSource messageSource, ApplicationEventPublisher publisher) {
        this.servicoLancamento = servicoLancamento;
        this.lancamentoRepository = lancamentoRepository;
        this.messageSource = messageSource;
        this.publisher = publisher;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public Page<Lancamento> pesquisar(LancamentoFiltro lancamentoFiltro, Pageable pageable){
        return lancamentoRepository.filtrar(lancamentoFiltro, pageable);
    }

    @GetMapping(params = "resumo")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public Page<ResumoLancamento> resumir(LancamentoFiltro lancamentoFiltro, Pageable pageable){
        return lancamentoRepository.resumir(lancamentoFiltro, pageable);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo){
        Lancamento lancamento = lancamentoRepository.findOne(codigo);
        return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
        Lancamento lancamentoSalvo = servicoLancamento.salvar(lancamento);
        publisher.publishEvent(new EventoRecursoCriado(this, response, lancamentoSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo){
        lancamentoRepository.delete(codigo);
    }

    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex){
        String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<MyMoneyExceptionHandler.Erro> erros = Arrays.asList(new MyMoneyExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }

}
