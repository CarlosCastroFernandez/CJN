package domain;

import clase.ActividadDiaria;

import java.util.ArrayList;

/**
 * La interfaz ActividadDiariaDAO define los métodos para manipular las Actividades Diarias en el almacenamiento de datos.
 */
public interface ActividadDiariaDAO {

    /**
     * Recupera todas las actividades diarias asociadas a un ID específico.
     *
     * @param id Identificador único utilizado para buscar actividades diarias.
     * @return Una lista de actividades diarias asociadas al ID proporcionado.
     */
    public ArrayList<ActividadDiaria> loadall(Integer id);

    /**
     * Inserta una nueva actividad diaria en el almacenamiento de datos.
     *
     * @param activity Objeto de tipo ActividadDiaria que se va a insertar.
     * @return La actividad diaria insertada.
     */
    public ActividadDiaria injection(ActividadDiaria activity);

    /**
     * Actualiza una actividad diaria existente en el almacenamiento de datos.
     *
     * @param activity Objeto de tipo ActividadDiaria que se va a actualizar.
     * @return La actividad diaria actualizada.
     */
    public ActividadDiaria update(ActividadDiaria activity);
}
