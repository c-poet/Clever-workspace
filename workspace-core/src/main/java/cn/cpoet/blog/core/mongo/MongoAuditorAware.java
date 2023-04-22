package cn.cpoet.blog.core.mongo;

import cn.cpoet.blog.api.context.AppContextHolder;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 审计信息
 *
 * @author CPoet
 */
@Component
public class MongoAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.ofNullable(AppContextHolder.getAuthContext().curSubject().getId());
    }
}
