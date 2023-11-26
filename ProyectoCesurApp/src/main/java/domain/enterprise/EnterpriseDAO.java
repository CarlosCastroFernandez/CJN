package domain.enterprise;

import classes.Enterprise;

import java.util.ArrayList;

/**
 * La interfaz EmpresaDAO define métodos para interactuar con el almacenamiento de datos de las empresas.
 */
public interface EnterpriseDAO {
    /**
     * Carga la información de una empresa basada en su ID.
     *
     * @param id Identificador único de la empresa.
     * @return Un objeto Empresa que contiene la información cargada.
     */
    public Enterprise load(Integer id);

    /**
     * Carga todas las empresas disponibles.
     *
     * @return Una lista de objetos Empresa que contiene todas las empresas disponibles.
     */
    public ArrayList<Enterprise> loadAll();

    /**
     * Actualiza la información de una empresa en el almacenamiento de datos.
     *
     * @param enterprise Objeto de tipo Empresa que se va a actualizar.
     * @return El objeto Empresa actualizado.
     */
    public Enterprise update(Enterprise enterprise);

    /**
     * Realiza una inyección de dependencia para un objeto Empresa.
     *
     * @param enterprise Objeto de tipo Empresa en el cual se realizará la inyección.
     * @return El objeto Empresa con la inyección de dependencia aplicada.
     */
    public Enterprise injection(Enterprise enterprise);

    /**
     * Elimina una empresa del almacenamiento de datos.
     *
     * @param enterprise Objeto de tipo Empresa que se va a eliminar.
     */
    public void delete(Enterprise enterprise);
}
