package com.example.cesurspringboot.repositories;

import com.example.cesurspringboot.classes.Alumn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryAlumn extends JpaRepository<Alumn,Long> {

    public Boolean existsAlumnByDni(String dni);
    public Alumn getAlumnByDni(String dni);

}
