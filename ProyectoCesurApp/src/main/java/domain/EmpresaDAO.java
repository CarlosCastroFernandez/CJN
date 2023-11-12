package domain;

import clase.Alumno;
import clase.Empresa;
import clase.Usuario;

import java.util.ArrayList;

public interface EmpresaDAO {
    public Empresa loadEnterprise(String nombre);
    public ArrayList<Empresa> loadAllEnterprise();
    public Empresa update(Empresa empresa);
}
