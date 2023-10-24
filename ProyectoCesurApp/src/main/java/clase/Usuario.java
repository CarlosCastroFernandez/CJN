package clase;

import exception.ApellidoConNumero;
import exception.DNIInvalido;
import exception.NombreConNumero;

public class Usuario {
     private Integer id;
     private String nombre;
     private String apellido1;
     private String apellido2;
     private String password;
     private String correo;
     private String dni;
     private Integer telefono;

     public Usuario(String nombre,String apellido1,String apellido2,
                    String password,String correo,String dni,Integer telefono) {
          this.id=null;
          this.nombre=nombre;
          this.apellido1=apellido1;
          this.apellido2=apellido2;
          this.password =password;
          this.correo=correo;
          this.dni =dni;
          this.telefono=telefono;
     }

     public Integer getId() {
          return id;
     }

     public void setId(Integer id) {
          this.id = id;
     }

     public Usuario() {

     }
     public Integer getTelefono() {
          return telefono;
     }
     public void setTelefono(Integer telefono) {
          this.telefono = telefono;
     }

     public String getNombre() {
          return nombre;
     }

     public String getApellido1() {
          return apellido1;
     }

     public String getApellido2() {
          return apellido2;
     }

     public String getPassword() {
          return password;
     }

     public String getCorreo() {
          return correo;
     }

     public String getDni() {
          return dni;
     }

     public void setNombre(String nombre) throws NombreConNumero {
          String numeros="0123456789";
          for(int i=0;i<nombre.length();i++) {
               if(numeros.contains(""+nombre.charAt(i))) {
                    throw new NombreConNumero("El nombre no puede contener numeros");
               }
          }
          this.nombre = nombre;
     }

     public void setApellido1(String apellido1) throws ApellidoConNumero {
          String numeros="0123456789";
          for(int i=0;i<apellido1.length();i++) {
               if(numeros.contains(""+apellido1.charAt(i))) {
                    throw new ApellidoConNumero("El apellido no puede contener numeros");
               }
          }
          this.apellido1 = apellido1;
     }

     public void setApellido2(String apellido2) throws ApellidoConNumero {
          String numeros="0123456789";
          for(int i=0;i<apellido2.length();i++) {
               if(numeros.contains(""+apellido2.charAt(i))) {
                    throw new ApellidoConNumero("El apellido no puede contener numeros");
               }
          }
          this.apellido2 = apellido2;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public void setCorreo(String correo) {
          this.correo = correo;
     }

     @Override
     public String toString() {
          return "Usuario{" +
                  "id=" + id +
                  ", nombre='" + nombre + '\'' +
                  ", apellido1='" + apellido1 + '\'' +
                  ", apellido2='" + apellido2 + '\'' +
                  ", password='" + password + '\'' +
                  ", correo='" + correo + '\'' +
                  ", dni='" + dni + '\'' +
                  ", telefono=" + telefono +
                  '}';
     }

     public void setDni(String dni) throws DNIInvalido {
          String letras="abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
          for(byte i=0;i<dni.length();i++) {
               if(!letras.contains(""+dni.charAt(i))&&dni.length()-1>i) {
                    this.dni = dni;
               }else if(letras.contains(""+dni.charAt(i))&&dni.length()-1>i) {
                    throw new DNIInvalido("El dni es invalido");
               }
          }

     }
}
