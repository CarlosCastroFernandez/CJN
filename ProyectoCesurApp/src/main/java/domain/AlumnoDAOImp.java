package domain;

import clase.Alumno;
import enums.Curso;
import exception.*;
import lombok.extern.java.Log;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Log
public class AlumnoDAOImp implements AlumnoDAO{
    private static Connection connection;
    private final static String queryLoad = "select * from alumno where dni = ?";

    private final static String queryloadAll="select * from alumno where profesor=?";

    private final static String queryRegister = "insert into alumno(dni, email, nombre, apellido1, apellido2, telefono, " +
            "contraseña, profesor, empresa, fechaNacimiento, horasDual, horasFCT, curso, observaciones)" + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public AlumnoDAOImp(Connection c){ connection = c;}

    @Override
    public Alumno loadActivity(String dni, String contrasenha) throws ContrasenhaIncorrecta, UsuarioInexistente {
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

                if(!contrasenha.equals(salida.getPassword())){
                    throw new ContrasenhaIncorrecta ("Contraseña Incorrecta");
                }
            }else{
                throw new UsuarioInexistente("No Existe El Usuario");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NombreConNumero e) {
            throw new RuntimeException(e);
        } catch (DNIInvalido e) {
            throw new RuntimeException(e);
        } catch (ApellidoConNumero e) {
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

    @Override
    public Alumno injection(Alumno alumno) {
        Alumno alum=null;
        try {
            PreparedStatement pst=connection.prepareStatement(queryRegister, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,alumno.getDni());
            pst.setString(2,alumno.getCorreo());
            pst.setString(3,alumno.getNombre());
            pst.setString(4,alumno.getApellido1());
            pst.setString(5,alumno.getApellido2());
            pst.setInt(6,alumno.getTelefono());
            pst.setString(7,alumno.getPassword());
            pst.setInt(8,alumno.getProfesorId());
            pst.setInt(9,alumno.getEmpresaId());
            pst.setString(10,alumno.getFechaNacimiento());
            pst.setString(11,alumno.getHorasDUAL());
            pst.setString(12,alumno.getHorasFCT());
            pst.setString(13,alumno.getCurso().name());
            pst.setString(14,alumno.getObservaciones());
            int filas=pst.executeUpdate();
            if(filas==1){
                ResultSet rs=pst.getGeneratedKeys();
                rs.next();
                alum=alumno;
                alum.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alum;
    }
}
