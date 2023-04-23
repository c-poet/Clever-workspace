package cn.cpoet.blog.repo.repository;

import cn.cpoet.blog.model.domain.Article;
import cn.cpoet.blog.repo.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author CPoet
 */
@Repository
public interface ArticleRepository extends BaseRepository<Article> {
}
