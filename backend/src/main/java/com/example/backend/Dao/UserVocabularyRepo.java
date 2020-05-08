package com.example.backend.Dao;

import com.example.backend.Entity.UserVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserVocabularyRepo extends JpaRepository<UserVocabulary,Integer> {

    List<UserVocabulary> findAllByUid(int id);

    UserVocabulary findByWord(String word);

    @Modifying
    void deleteByWord(String word);
}
