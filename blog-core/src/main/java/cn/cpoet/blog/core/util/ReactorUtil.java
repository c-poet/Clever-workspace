package cn.cpoet.blog.core.util;

import reactor.core.publisher.SynchronousSink;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 响应式工具
 *
 * @author CPoet
 */
public abstract class ReactorUtil {
    private ReactorUtil() {
    }

    public static <T> BiConsumer<T, SynchronousSink<T>> handle(Consumer<T> handler) {
        return (val, sink) -> {
            try {
                handler.accept(val);
                sink.complete();
            } catch (Exception e) {
                sink.error(e);
            }
        };
    }

    public static <T, R> BiConsumer<T, SynchronousSink<R>> handle(BiConsumer<T, SynchronousSink<R>> handler) {
        return (val, sink) -> {
            try {
                handler.accept(val, sink);
            } catch (Exception e) {
                sink.error(e);
            }
        };
    }
}
