package classes;

import exception.*;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * La clase Profesor representa un docente en el sistema educativo.
 * Extiende la clase Usuario.
 */
public class Teacher extends User {

    /**
     * Imagen de perfil del profesor.
     */
    private Image profilePic;

    /**
     * Lista de alumnos asociados al profesor.
     */
    private ArrayList<Alumn> alumn;

    /**
     * Constructor de la clase Profesor que recibe varios parámetros para inicializar sus atributos.
     *
     * @param id Identificador único del profesor.
     * @param name Nombre del profesor.
     * @param lastName Primer apellido del profesor.
     * @param lastName2 Segundo apellido del profesor.
     * @param password Contraseña del profesor.
     * @param email Correo electrónico del profesor.
     * @param dni Número de identificación del profesor (DNI).
     * @param phone Número de teléfono del profesor.
     * @throws NameWithNumber Si el nombre contiene números.
     * @throws LastNameWithNumber Si el apellido contiene números.
     * @throws InvalidDNI Si el formato del DNI es inválido.
     */
    public Teacher(Integer id, String name, String lastName, String lastName2, String password, String email, String dni, Integer phone) throws NameWithNumber, LastNameWithNumber, InvalidDNI {
        super(id,name, lastName, lastName2, password, email, dni, phone);
        this.alumn = null;
        this.setName(name);
        this.setLastName(lastName);
        this.setLastName2(lastName2);
        this.setPassword(password);
        this.setEmail(email);
        this.setDni(dni);
        this.setPhone(phone);
    }

    /**
     * Constructor de la clase Profesor que recibe nombre de usuario y contraseña.
     * Inicializa la lista de alumnos como una lista vacía.
     *
     * @param name Nombre del profesor.
     * @param password Contraseña del profesor.
     * @throws NonExistentUser Si el usuario no existe.
     * @throws IncorrectPassword Si la contraseña es incorrecta.
     */
    public Teacher(String name, String password) throws NonExistentUser, IncorrectPassword {
        this.alumn =new ArrayList<Alumn>();

    }

    /**
     * Constructor vacío de la clase Profesor.
     */
    public Teacher(){

    }

    //Getters y Setters de la clase Profesor.
    public Image getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Image profilePic) {
        this.profilePic = profilePic;
    }

    public ArrayList<Alumn> getAlumn() {
        return alumn;
    }

    public void setAlumn(ArrayList<Alumn> alumn) {
        this.alumn = alumn;
    }
}
