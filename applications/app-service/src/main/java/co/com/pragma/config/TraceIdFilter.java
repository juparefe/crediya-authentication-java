package co.com.pragma.config;

import org.slf4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Component
public class TraceIdFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(TraceIdFilter.class);

    public static final String TRACE_ID_KEY = "traceId";
    public static final String TRACE_HEADER = "X-Trace-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String traceId = Optional.ofNullable(exchange.getRequest().getHeaders().getFirst(TRACE_HEADER))
                .orElse(UUID.randomUUID().toString());

        exchange.getResponse().getHeaders().add(TRACE_HEADER, traceId);

        exchange.getAttributes().put(TRACE_ID_KEY, traceId);
        log.info("TraceIdFilter activo: {}",traceId);

        return chain.filter(exchange)
                .contextWrite(ctx -> ctx.put(TRACE_ID_KEY, traceId));
    }
}
