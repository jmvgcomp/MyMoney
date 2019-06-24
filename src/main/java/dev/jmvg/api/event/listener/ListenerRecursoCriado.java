package dev.jmvg.api.event.listener;

import dev.jmvg.api.event.EventoRecursoCriado;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class ListenerRecursoCriado implements ApplicationListener<EventoRecursoCriado> {
    @Override
    public void onApplicationEvent(EventoRecursoCriado eventoRecursoCriado) {
        HttpServletResponse response = eventoRecursoCriado.getResponse();
        Long codigo = eventoRecursoCriado.getCodigo();

        adicionarHeaderLocation(response, codigo);
    }

    private void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(codigo).toUri();
        response.setHeader("Location", uri.toASCIIString());

    }
}
