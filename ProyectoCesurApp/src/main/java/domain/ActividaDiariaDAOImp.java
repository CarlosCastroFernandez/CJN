package domain;

import clase.ActividadDiaria;

import java.sql.Connection;
import java.util.ArrayList;

public class ActividaDiariaDAOImp implements ActividadDiariaDAO{
    private static Connection conexion;
   public ActividaDiariaDAOImp(Connection conn){
       conexion=conn;
   }
    @Override
    public ArrayList<ActividadDiaria> loadall(Integer id) {
        return null; //select * from actividadDiaria where alumnoId = ?"
    }
    //
}
