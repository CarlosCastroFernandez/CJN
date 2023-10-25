package domain;

import clase.ActividadDiaria;
import clase.Sesion;
import enums.TipoPractica;
import exception.ContrasenhaIncorrecta;
import exception.UsuarioInexistente;

import java.sql.*;
import java.util.ArrayList;

public class ActividaDiariaDAOImp implements ActividadDiariaDAO{
    private static Connection conexion;
    private static String loadActivity = "select * from actividadDiaria where alumnoId = ?";
    private static String insertActivity = "insert into actividadDiaria(nombre, totalHoras, observaciones, tipoPractica, fecha, alumnoId)\n"+"VALUE (?, ?, ?, ?, ?, ?)";
   public ActividaDiariaDAOImp(Connection conn){
       conexion=conn;
   }

    @Override
    public ArrayList<ActividadDiaria> loadall(Integer id) {
        ArrayList<ActividadDiaria> actividadDiaria = new ArrayList<>();
        try {
            PreparedStatement pst = conexion.prepareStatement(loadActivity);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ActividadDiaria actividad = new ActividadDiaria();
                actividad.setId(rs.getInt("id"));
                actividad.setIdAlumno(rs.getInt("alumnoId"));
                actividad.setFecha(String.valueOf(rs.getDate("fecha")));
                actividad.setTipoPractica(TipoPractica.valueOf(rs.getString("tipoPractica")));
                actividad.setTotalHoras(rs.getInt("totalHoras"));
                actividad.setNombreTarea(rs.getString("nombre"));
                actividad.setObservaciones(rs.getString("observaciones"));

                actividadDiaria.add(actividad);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actividadDiaria;
    }

    @Override
    public ActividadDiaria insercion(ActividadDiaria actividad) {
       ActividadDiaria actividades=null;
       try {
           PreparedStatement pst = conexion.prepareStatement(insertActivity, Statement.RETURN_GENERATED_KEYS);
           pst.setString(1, actividad.getNombreTarea());
           pst.setInt(2, actividad.getTotalHoras());
           pst.setString(3, actividad.getObservaciones());
           pst.setString(4, String.valueOf(actividad.getTipoPractica()));
           pst.setString(5, actividad.getFecha());
           pst.setInt(6,Sesion.getAlumno().getId());
           actividades=actividad;
           Integer fila = pst.executeUpdate();
           if(fila==1){
               ResultSet rs=pst.getGeneratedKeys();
               rs.next();
               Integer numero=rs.getInt(1);
               actividades.setId(numero);
           }
           pst.close();

       } catch (SQLException e){
           throw new RuntimeException(e);
       }
       return actividades;
    }
}
