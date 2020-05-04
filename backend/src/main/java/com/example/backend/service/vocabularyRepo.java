package com.example.backend.service;

import com.example.backend.Entity.vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface vocabularyRepo extends JpaRepository<vocabulary,Integer> {
    @Query(value="select * from vocabulary where word like concat('%',?1,'%') ORDER BY " +
            "CASE " +
            "WHEN word=?1 THEN 1 " +
            "WHEN word like concat(?1,'%') THEN 2 " +
            "WHEN word like concat('%',?1) THEN 3 " +
            "WHEN word like concat('%',?1,'%') THEN 4 " +
            "ELSE 5 " +
            "END " +
            "limit ?2 ;",nativeQuery=true)
    List<vocabulary> findAllByWordLike(String word,Integer top);
}
