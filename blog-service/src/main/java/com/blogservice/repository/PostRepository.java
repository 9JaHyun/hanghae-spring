package com.blogservice.repository;

import com.blogservice.domain.PostEntity;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// DB와 연관되는 애들 -> 격리
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    PostEntity findByPassword(String password);

    // 방법1. DB에서 부터 정렬해서 가져오기
    @Query("select p from PostEntity p order by p.id")
    List<PostEntity> sortById();

    List<PostEntity> findAll(Sort sor);
}
