package cn.cpoet.blog.repo.repository;

import cn.cpoet.blog.model.domain.PermissionAcl;
import cn.cpoet.blog.repo.base.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author CPoet
 */
@Repository
public interface PermissionAclRepository extends BaseRepository<PermissionAcl> {
}
