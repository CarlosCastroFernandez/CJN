package com.example.cesurspringboot.repositories;

import com.example.cesurspringboot.classes.Alumn;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Rpositorio de Alumn
 */
public interface RepositoryAlumn extends JpaRepository<Alumn,Long> {
    /**
     * Verifica si existe un alumno por su dni.
     * @param dni del alumno.
     * @return Si el alumno en concreto existe o no.
     */
    public Boolean existsAlumnByDni(String dni);
    /**
     * Trae al alumno por su dni.
     * @param dni del alumno.
     * @return El alumno que coincide con el dni.
     */
    public Alumn getAlumnByDni(String dni);

}
