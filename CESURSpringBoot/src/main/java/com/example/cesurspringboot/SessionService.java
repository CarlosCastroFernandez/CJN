package com.example.cesurspringboot;

import com.example.cesurspringboot.classes.Alumn;
import com.example.cesurspringboot.repositories.RepositoryAlumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    RepositoryAlumn repo;

    public Alumn alumnoActivo(){
        return repo.findAll().get(0);
    }
}
