package cn.cpoet.blog.repo.mongo;

import cn.cpoet.blog.api.context.AppContextHolder;
import cn.cpoet.blog.api.context.Subject;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 审计信息
 *
 * @author CPoet
 */
@Component
public class MongoAuditorAware implements ReactiveAuditorAware<Long> {

    @Override
    public Mono<Long> getCurrentAuditor() {
        Subject subject = AppContextHolder.getAuthContext().curSubject();
        return null;
    }
}
