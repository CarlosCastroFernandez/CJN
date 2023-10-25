package domain;

import clase.ActividadDiaria;

import java.util.ArrayList;

public interface ActividadDiariaDAO {
    public ArrayList<ActividadDiaria> loadall(Integer id);
}
