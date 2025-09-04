package co.com.pragma.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.reactivestreams.Subscription;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Operators;
import reactor.util.context.Context;

@Configuration
public class ReactorMdcConfiguration {

    private static final String HOOK_KEY = "mdcContextLifter";
    private static final String TRACE_ID_KEY = TraceIdFilter.TRACE_ID_KEY;

    @PostConstruct
    public void setupHook() {
        Hooks.onEachOperator(HOOK_KEY, Operators.lift((scannable, subscriber) ->
                new CoreSubscriber<Object>() {
                    @Override public void onSubscribe(Subscription s) { subscriber.onSubscribe(s); }
                    @Override public void onNext(Object t) { withMdc(() -> subscriber.onNext(t)); }
                    @Override public void onError(Throwable t) { withMdc(() -> subscriber.onError(t)); }
                    @Override public void onComplete() { withMdc(subscriber::onComplete); }
                    @Override public Context currentContext() { return subscriber.currentContext(); }

                    private void withMdc(Runnable r) {
                        String traceId = currentContext().getOrDefault(TRACE_ID_KEY, null);
                        if (traceId != null) MDC.put(TRACE_ID_KEY, traceId);
                        try { r.run(); }
                        finally { MDC.remove(TRACE_ID_KEY); }
                    }
                }
        ));
    }

    @PreDestroy
    public void cleanupHook() {
        Hooks.resetOnEachOperator(HOOK_KEY);
    }
}
