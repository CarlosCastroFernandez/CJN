package domain;

import clase.Empresa;

import java.util.ArrayList;

/**
 * La interfaz EmpresaDAO define métodos para interactuar con el almacenamiento de datos de las empresas.
 */
public interface EmpresaDAO {
    /**
     * Carga la información de una empresa basada en su ID.
     *
     * @param id Identificador único de la empresa.
     * @return Un objeto Empresa que contiene la información cargada.
     */
    public Empresa loadEnterprise(Integer id);

    /**
     * Carga todas las empresas disponibles.
     *
     * @return Una lista de objetos Empresa que contiene todas las empresas disponibles.
     */
    public ArrayList<Empresa> loadAllEnterprise();

    /**
     * Actualiza la información de una empresa en el almacenamiento de datos.
     *
     * @param enterprise Objeto de tipo Empresa que se va a actualizar.
     * @return El objeto Empresa actualizado.
     */
    public Empresa update(Empresa enterprise);

    /**
     * Realiza una inyección de dependencia para un objeto Empresa.
     *
     * @param enterprise Objeto de tipo Empresa en el cual se realizará la inyección.
     * @return El objeto Empresa con la inyección de dependencia aplicada.
     */
    public Empresa injection(Empresa enterprise);

    /**
     * Elimina una empresa del almacenamiento de datos.
     *
     * @param enterprise Objeto de tipo Empresa que se va a eliminar.
     */
    public void delete(Empresa enterprise);
}
