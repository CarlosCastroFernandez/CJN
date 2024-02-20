package com.example.cesurspringboot.repositories;

import com.example.cesurspringboot.classes.Alumn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryAlumn extends JpaRepository<Alumn,Long> {

    public Boolean existsAlumnByEmail(String email);
    public Alumn getAlumnByEmail(String email);

}
