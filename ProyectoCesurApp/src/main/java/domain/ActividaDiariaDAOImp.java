package domain;

import clase.ActividadDiaria;
import clase.Sesion;
import enums.TipoPractica;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;

@Log public class ActividaDiariaDAOImp implements ActividadDiariaDAO{
    private static Connection conexion;
    private static String loadActivity = "select * from actividadDiaria where alumnoId = ?";
    private static String insertActivity = "insert into actividadDiaria(nombre, totalHoras, observaciones, tipoPractica, fecha, alumnoId)\n"+"VALUE (?, ?, ?, ?, ?, ?)";
    private static String actualizacion = "update actividadDiaria set nombre=?, totalHoras=?, observaciones=?, tipoPractica=?, fecha=?, alumnoId=?";
  private final static String DELETEACTIVIDAD="delete from actividadDiaria where id=?";
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
                actividad.setIdAlumn(rs.getInt("alumnoId"));
                actividad.setDate(String.valueOf(rs.getDate("fecha")));
                actividad.setPracticeType(TipoPractica.valueOf(rs.getString("tipoPractica")));
                actividad.setTotaHours(rs.getInt("totalHoras"));
                actividad.setTaskName(rs.getString("nombre"));
                actividad.setObservations(rs.getString("observaciones"));
                actividadDiaria.add(actividad);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actividadDiaria;
    }

    @Override
    public ActividadDiaria injection(ActividadDiaria actividad) {
       ActividadDiaria actividades=null;
       try {
           PreparedStatement pst = conexion.prepareStatement(insertActivity, Statement.RETURN_GENERATED_KEYS);
           pst.setString(1, actividad.getTaskName());
           pst.setInt(2, actividad.getTotalHours());
           pst.setString(3, actividad.getObservations());
           pst.setString(4, String.valueOf(actividad.getPracticeType()));
           pst.setString(5, actividad.getDate());
           pst.setInt(6,Sesion.getAlumn().getId());
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

    @Override
    public ActividadDiaria update(ActividadDiaria actividadDiaria) {
        ActividadDiaria actividad = actividadDiaria;
        try{
            PreparedStatement preparedStatement = conexion.prepareStatement(actualizacion);
            preparedStatement.setString(1, actividad.getTaskName());
            preparedStatement.setInt(2, actividad.getTotalHours());
            preparedStatement.setString(3, actividad.getObservations());
            preparedStatement.setString(4, String.valueOf(actividad.getPracticeType()));
            preparedStatement.setString(5, actividad.getDate());
            preparedStatement.setInt(6, Sesion.getAlumn().getId());
            int filas = preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return actividad;
    }

    public void deleteActividad(ActividadDiaria acD){
        try {
            PreparedStatement pst=conexion.prepareStatement(DELETEACTIVIDAD);
            pst.setInt(1,acD.getId());
            int filas=pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
