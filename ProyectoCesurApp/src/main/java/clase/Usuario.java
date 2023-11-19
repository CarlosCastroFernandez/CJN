package clase;

import exception.ApellidoConNumero;
import exception.DNIInvalido;
import exception.NombreConNumero;

/**
 * La clase Usuario represnta a un usuario del sistema.
 */
public class Usuario {
     /**
      * Identificador único del usuario.
      */
     private Integer id;
     /**
      * Nombre del usuario.
      */
     private String name;
     /**
      * Primer apellido del usuario.
      */
     private String lastName;
     /**
      * Segundo apellido del usuario.
      */
     private String lastName2;
     /**
      * Contraseña del usuario.
      */
     private String password;
     /**
      * Correo electrónico del usuario.
      */
     private String email;
     /**
      * DNI del usuario.
      */
     private String dni;
     /**
      * Número de teléfono del usuario.
      */
     private Integer phone;

     /**
      * Constructor de la clase Usuario.
      * @param id Identificador único del usuario.
      * @param name Nombre del usuario.
      * @param lastName Primer apellido del usuario.
      * @param lastName2 Segundo apellido del usuario.
      * @param password Contraseña del usuario.
      * @param email Correo electrónico del usuario.
      * @param dni Número de identificación del usuario (DNI).
      * @param phone Número de teléfono del usuario.
      */
     public Usuario(Integer id,String name,String lastName,String lastName2,
                    String password,String email,String dni,Integer phone) {
          this.id = id;
          this.name = name;
          this.lastName = lastName;
          this.lastName2 = lastName2;
          this.password = password;
          this.email = email;
          this.dni = dni;
          this.phone = phone;
     }

     /**
      * Constructor por defecto de la clase Usuario.
      */
     public Usuario() {

     }

     //Getters y Setters para todos los atributos de la clase.
     public Integer getId() {
          return id;
     }

     public void setId(Integer id) {
          this.id = id;
     }

     public String getName() {
          return name;
     }

     public String getLastName() {
          return lastName;
     }

     public String getLastName2() {
          return lastName2;
     }

     public String getPassword() {
          return password;
     }

     public String getEmail() {
          return email;
     }

     public String getDni() {
          return dni;
     }
     
     public Integer getPhone() {
          return phone;
     }

     public void setPhone(Integer phone) {
          this.phone = phone;
     }
     
     /**
      * Establece el nombre del usuario.
      * @param name Nombre a establecer para el usuario.
      * @throws NombreConNumero Si el nombre contiene números.
      */
     public void setName(String name) throws NombreConNumero {
          String numbers="0123456789";
          for(int i=0;i<name.length();i++) {
               if(numbers.contains(""+name.charAt(i))) {
                    throw new NombreConNumero("El nombre no puede contener números");
               }
          }
          this.name = name;
     }

     /**
      * Establece el primer apellido del usuario.
      * @param lastName Primer apellido a establecer para el usuario.
      * @throws ApellidoConNumero Si el apellido contiene números.
      */
     public void setLastName(String lastName) throws ApellidoConNumero {
          String numbers="0123456789";
          for(int i=0;i<lastName.length();i++) {
               if(numbers.contains(""+lastName.charAt(i))) {
                    throw new ApellidoConNumero("El apellido no puede contener números");
               }
          }
          this.lastName = lastName;
     }

     /**
      * Establece el segundo apellido del usuario.
      * @param lastName2 Segundo apellido a establecer para el usuario.
      * @throws ApellidoConNumero Si el apellido contiene números.
      */
     public void setLastName2(String lastName2) throws ApellidoConNumero {
          String numbers="0123456789";
          for(int i=0;i<lastName2.length();i++) {
               if(numbers.contains(""+lastName2.charAt(i))) {
                    throw new ApellidoConNumero("El apellido no puede contener números");
               }
          }
          this.lastName2 = lastName2;
     }

     /**
      * Establece la contraseña del usuario.
      * @param password Contraseña a establecer para el usuario.
      */
     public void setPassword(String password) {
          this.password = password;
     }

     /**
      * Establece el correo electrónico del usuario.
      * @param email Correo electrónico a establecer para el usuario.
      */
     public void setEmail(String email) {
          this.email = email;
     }

     /**
      * Establece el número de identificación del usuario (DNI).
      * @param dni Número de identificación a establecer para el usuario.
      * @throws DNIInvalido Si el formato del DNI es inválido.
      */
     public void setDni(String dni) throws DNIInvalido {
          String letters="abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
          for(byte i=0;i<dni.length();i++) {
               if(!letters.contains(""+dni.charAt(i))&&dni.length()-1>i) {
                    this.dni = dni;
               }else if(letters.contains(""+dni.charAt(i))&&dni.length()-1>i) {
                    throw new DNIInvalido("El dni es invalido");
               }
          }
     }

     /**
      * Override del método toString para obtener una representación en cadena de la clase Usuario.
      * @return Cadena que representa al usuario con todos sus atributos.
      */
     @Override
     public String toString() {
          return "Usuario{" +
                  "id=" + id +
                  ", nombre='" + name + '\'' +
                  ", apellido1='" + lastName + '\'' +
                  ", apellido2='" + lastName2 + '\'' +
                  ", password='" + password + '\'' +
                  ", correo='" + email + '\'' +
                  ", dni='" + dni + '\'' +
                  ", telefono=" + phone +
                  '}';
     }
}
