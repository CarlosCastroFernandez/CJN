package com.example.cesurspringboot.repositories;

import com.example.cesurspringboot.classes.Alumn;
import com.example.cesurspringboot.classes.DailyActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositoryDailyActivity extends JpaRepository<DailyActivity,Long> {

    @Query ("SELECT a FROM DailyActivity a where a.idAlumn=:alumno")
    public List<DailyActivity> getAllByIdAlumn(@Param("alumno") Alumn alumno);
}
