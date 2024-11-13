package cn.hui_community.service.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageHelper {

  public static <T> Page<T> from(List<T> list, Pageable pageable) {
    return new PageImpl<>(list, pageable, list.size());
  }

  public static <T> Page<T> from(List<T> list, Pageable pageable, Long total) {
    return new PageImpl<>(list, pageable, total);
  }

  public static <X, Y> Page<Y> map(Page<X> page, Function<X, Y> function) {
    List<Y> collected = page.getContent().stream().map(function).toList();
    return new PageImpl<>(collected, page.getPageable(), page.getTotalElements());
  }
}
