package com.example.backend.Dao;

import com.example.backend.Entity.StopWords;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StopWordsRepo extends JpaRepository<StopWords,Integer> {
}
