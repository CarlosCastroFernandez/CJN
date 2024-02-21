package com.example.cesurspringboot.repositories;

import com.example.cesurspringboot.classes.Alumn;
import com.example.cesurspringboot.classes.DailyActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio de DailyActivity.
 */
public interface RepositoryDailyActivity extends JpaRepository<DailyActivity,Long> {

    /**
     * Recupera todas las actividades diarias de un alumno específico.
     *
     * @param alumno El objeto Alumn que representa al alumno.
     * @return Una lista de actividades diarias asociadas al alumno.
     */
    @Query ("SELECT a FROM DailyActivity a where a.idAlumn=:alumno")
    public List<DailyActivity> getAllByIdAlumn(@Param("alumno") Alumn alumno);
}
