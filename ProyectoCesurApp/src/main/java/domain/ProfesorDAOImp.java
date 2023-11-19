package domain;

import clase.Profesor;
import exception.*;
import lombok.extern.java.Log;

import java.sql.*;

@Log
public class ProfesorDAOImp implements ProfesorDAO{

    private static Connection conexion;
    private static String saveTeacher="select * from profesor where dni=?";
    private static String saveTeacherById="select * from profesor where id=?";
    private static String registerTeacher="insert into profesor(dni,nombre,email,apellido1,apellido2,contraseña,telefono)\n" +
            "VALUE (?,?,?,?,?,?,?)";
    public ProfesorDAOImp(Connection conn){
        conexion=conn;
    }

    @Override
    public Profesor loadTeacher(String dni,String contrasenha) throws UsuarioInexistente, ContrasenhaIncorrecta {

        Profesor profesor=null;
        try {
            PreparedStatement pst=conexion.prepareStatement(saveTeacher);
            pst.setString(1,dni);
            ResultSet rs=pst.executeQuery();

            if(rs.next()){
                profesor=new Profesor(dni, contrasenha);
                profesor.setId(rs.getInt("id"));
                profesor.setDni(dni);
                profesor.setName(rs.getString("nombre"));
                profesor.setPassword(rs.getString("contraseña"));
                if(!contrasenha.equals(profesor.getPassword())){
                    throw new ContrasenhaIncorrecta("Contraseña Incorrecta");
                }
                profesor.setLastName(rs.getString("apellido1"));
                profesor.setLastName2(rs.getString("apellido2"));
                profesor.setEmail(rs.getString("email"));
                profesor.setPhone(rs.getInt("telefono"));
            }else{
                log.warning("Usuario no existe");
                throw new UsuarioInexistente("Usuario no existe");
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
        return profesor;
    }
    public Profesor loadTeacherById(Integer id) throws UsuarioInexistente {

        Profesor profesor=null;
        try {
            PreparedStatement pst=conexion.prepareStatement(saveTeacherById);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();

            if(rs.next()){
                profesor=new Profesor();
                profesor.setId(rs.getInt("id"));
                profesor.setDni(rs.getString("dni"));
                profesor.setName(rs.getString("nombre"));
                profesor.setPassword(rs.getString("contraseña"));
                profesor.setLastName(rs.getString("apellido1"));
                profesor.setLastName2(rs.getString("apellido2"));
                profesor.setEmail(rs.getString("email"));
                profesor.setPhone(rs.getInt("telefono"));
            }else{
                log.warning("Usuario no existe");
                throw new UsuarioInexistente("Usuario no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DNIInvalido e) {
            throw new RuntimeException(e);
        } catch (NombreConNumero e) {
            throw new RuntimeException(e);
        } catch (ApellidoConNumero e) {
            throw new RuntimeException(e);
        }
        return profesor;
    }


    @Override
    public Profesor injection(Profesor profesor) {
        Profesor profe=new Profesor();
        try {
            PreparedStatement pst=conexion.prepareStatement(registerTeacher, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,profesor.getDni());
            pst.setString(2,profesor.getName());
            pst.setString(3,profesor.getEmail());
            pst.setString(4,profesor.getLastName());
            pst.setString(5,profesor.getLastName2());
            pst.setString(6, profesor.getPassword());
            pst.setInt(7,profesor.getPhone());
            Integer fila=pst.executeUpdate();
            if(fila==1){
                ResultSet rs=pst.getGeneratedKeys();
                rs.next();
                profe=profesor;
                profe.setId(rs.getInt(1));

            }
            pst.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return profe;
    }
}

