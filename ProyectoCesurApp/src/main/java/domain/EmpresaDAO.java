package domain;

import clase.Alumno;
import clase.Empresa;
import clase.Usuario;

import java.util.ArrayList;

public interface EmpresaDAO {
    public Empresa loadEnterprise(Integer id);
    public ArrayList<Empresa> loadAllEnterprise();
    public Empresa update(Empresa empresa);
    public Empresa insert(Empresa empresa);
    public void delete(Empresa empresa);
}
