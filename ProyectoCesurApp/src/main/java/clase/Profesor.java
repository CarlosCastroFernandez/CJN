package clase;

import exception.*;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Profesor extends Usuario{
    private Image imagenPerfil;
    private ArrayList<Alumno> alumnos;

    public Profesor(String nombre, String apellido1, String apellido2, String contrasenha, String correo, String dni,Integer telefono) throws NombreConNumero, ApellidoConNumero, DNIInvalido {
        super(nombre, apellido1, apellido2, contrasenha, correo, dni, telefono);
        this.alumnos = null;
        this.setNombre(nombre);
        this.setApellido1(apellido1);
        this.setApellido2(apellido2);
        this.setPassword(contrasenha);
        this.setCorreo(correo);
        this.setDni(dni);
        this.setTelefono(telefono);
    }
    public Profesor(){

    }

    public Profesor(String nombre,String contrasenha) throws UsuarioInexistente, ContrasenhaIncorrecta {
        this.alumnos =new ArrayList<Alumno>();

    }
    public Image getImagenPerfil() {
        return imagenPerfil;
    }
    public void setImagenPerfil(Image imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }
    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }
    public void setListaAlumnos(ArrayList<Alumno> ventanaProfesor) {
        this.alumnos = ventanaProfesor;
    }
}
