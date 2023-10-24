package domain;

import clase.Alumno;
import enums.Curso;
import exception.ApellidoConNumero;
import exception.DNIInvalido;
import exception.NombreConNumero;
import exception.UsuarioInexistente;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Log
public class AlumnoDAOImp implements AlumnoDAO{
    private static Connection connection;
    private final static String queryLoad = "select * from alumno where dni = ?";

    private final static String queryloadAll="select * from alumno where profesor=?";
    private final static String queryRegister = "insert into alumno(dni, email, nombre, apellido1, apellido2, telefono, " +
            "contrasenha, profesor, empresa, fechaNacimiento, horasDual, horasFCT, curso, observaciones)" + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public AlumnoDAOImp(Connection c){ connection = c;}

    @Override
    public Alumno loadActivity(String dni){
        Alumno salida = null;

        try {
            PreparedStatement pst=connection.prepareStatement(queryLoad);
            pst.setString(1,dni);
            ResultSet rs=pst.executeQuery();

            if(rs.next()){
                salida=new Alumno( rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"), rs.getNString("apellido2"),
                        rs.getString("contraseña"),rs.getString("email"), rs.getString("dni"),
                        rs.getInt("telefono"),rs.getString("horasDual"),rs.getString("horasFCT"),
                        rs.getInt("profesor"),String.valueOf(rs.getDate("fechaNacimiento")), rs.getInt("empresa"),
                        Curso.valueOf(rs.getString("curso")) ,rs.getString("observaciones"));
                salida.setProfesor(new ProfesorDAOImp(DBConnection.getConnection()).loadTeacherById(rs.getInt("profesor")));


            }else{
                log.warning("Usuario no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NombreConNumero e) {
            throw new RuntimeException(e);
        } catch (DNIInvalido e) {
            throw new RuntimeException(e);
        } catch (ApellidoConNumero e) {
            throw new RuntimeException(e);
        } catch (UsuarioInexistente e) {
            throw new RuntimeException(e);
        }
        return salida;

    }

    @Override
    public ArrayList<Alumno> loadAll(Integer id) {
        ArrayList<Alumno>  salida = new ArrayList<Alumno>();

        try {
            PreparedStatement pst=connection.prepareStatement(queryloadAll);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while(rs.next()){
                Alumno alumno=new Alumno( rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"),
                        rs.getString("contraseña"),rs.getString("email"), rs.getString("dni"),
                        rs.getInt("telefono"),rs.getString("horasDual"),rs.getString("horasFCT"),
                        rs.getInt("profesor"),sdf.format(rs.getDate("fechaNacimiento")), rs.getInt("empresa"),
                        Curso.valueOf(rs.getString("curso")) ,rs.getString("observaciones"));
                alumno.setProfesor(new ProfesorDAOImp(DBConnection.getConnection()).loadTeacherById(id));
                salida.add(alumno);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NombreConNumero e) {
            throw new RuntimeException(e);
        } catch (DNIInvalido e) {
            throw new RuntimeException(e);
        } catch (ApellidoConNumero e) {
            throw new RuntimeException(e);
        } catch (UsuarioInexistente e) {
            throw new RuntimeException(e);
        }
        return salida;
    }

    @Override
    public Alumno saveActivity(Alumno a) {
        return null;
    }

    @Override
    public Alumno updateActivity(Alumno a) {
        return null;
    }

    @Override
    public void removeActivity(Alumno a) {

    }
}
