package com.example.cesurspringboot.controllers;

import com.example.cesurspringboot.classes.Alumn;
import com.example.cesurspringboot.classes.DailyActivity;
import com.example.cesurspringboot.repositories.RepositoryDailyActivity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.cesurspringboot.repositories.RepositoryAlumn;


/**
 * Controlador para gestionar las actividades diarias de los alumnos.
 */
@Controller
@RequestMapping("/")
public class ControllerDailyActivity {

    /**
     * Acceso al repositorio de RepositoryDailyActivity.
     */
    @Autowired
    private RepositoryDailyActivity repositoryActivity;

    /**
     * Acceso al repositorio de RepositoryAlumn.
     */
    @Autowired
    private RepositoryAlumn repositoryAlumn;

    /**
     * Obtiene todas las actividades de un alumno por su ID.
     *
     * @param id      ID del alumno.
     * @param model   Modelo para la vista.
     * @param request Petición HTTP.
     * @return Vista con la lista de actividades del alumno o redirige al inicio de sesión.
     */
    @GetMapping("/{id}")
    public String getAllActivityByAlumnId(@PathVariable Long id, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Alumn alumnSession = (Alumn)session.getAttribute("alumno");

        if(alumnSession!=null){
            Alumn alumn = alumnSession;
            model.addAttribute("actividades",repositoryActivity.getAllByIdAlumn(alumn));
            System.out.println(repositoryActivity.getAllByIdAlumn(alumn));
            return "getAllActivityByAlumnId";
        }else{
            model.addAttribute("alumno", new Alumn());
            return "login";
        }
    }

    /**
     * Crea una nueva actividad diaria.
     *
     * @param dailyActivity Actividad diaria a crear.
     * @param request       Petición HTTP.
     * @param model         Modelo para la vista.
     * @return Redirige a la lista de actividades del alumno o al inicio de sesión.
     */
    @PostMapping("/new")
    public String newActividad(@ModelAttribute DailyActivity dailyActivity,HttpServletRequest request,Model model){
        /*Alumn alumno=repositoryAlumn.findById(Long.valueOf(21)).get();
        dailyActivity.setIdAlumn(alumno);*/
        HttpSession session = request.getSession();
        Alumn alumn = (Alumn) session.getAttribute("alumno");
        if(alumn != null){
            System.out.println(dailyActivity);
            dailyActivity.setIdAlumn(alumn);
            repositoryActivity.save(dailyActivity);
            return "redirect:/"+Long.valueOf( dailyActivity.getIdAlumn().getId()) ;
        }else{
            model.addAttribute("alumno", new Alumn());
            return "login";
        }
    }

    /**
     * Muestra el formulario para crear una nueva actividad diaria.
     *
     * @param model   Modelo para la vista.
     * @param request Petición HTTP.
     * @return Vista del formulario para crear una nueva actividad o redirige al inicio de sesión.
     */
    @GetMapping("/new")
    public String newActividad(Model model,HttpServletRequest request){
        DailyActivity dailyActivity = new DailyActivity();

        HttpSession session=request.getSession();
        Alumn alumn = (Alumn) session.getAttribute("alumno");
        if(alumn != null){
            dailyActivity.setIdAlumn(alumn);
            model.addAttribute("actividad",dailyActivity);
            return "editar";
        }else{
            model.addAttribute("alumno", new Alumn());
            return "login";
        }
    }

    /**
     * Muestra el formulario para editar una actividad.
     *
     * @param idActividad ID de la actividad a editar.
     * @param model       Modelo para almacenar datos.
     * @return La vista correspondiente.
     */
    @GetMapping("/edit/{idActividad}")
    public String editDailyActivity(@PathVariable Long idActividad, Model model){
        model.addAttribute("actividad",repositoryActivity.findById(idActividad).get());
        return "editar";
    }

    /**
     * Procesa el formulario para editar una actividad.
     *
     * @param idActividad   ID de la actividad a editar.
     * @param dailyActivity Objeto DailyActivity con los datos de la actividad editada.
     * @param request       Objeto HttpServletRequest para acceder a la sesión.
     * @return La redirección a la vista correspondiente.
     */
    @PostMapping("/edit/{idActividad}")
    public String editActivityPost(@PathVariable Long idActividad, @ModelAttribute DailyActivity dailyActivity,HttpServletRequest request){
       /* Alumn alumno=repositoryAlumn.findById(Long.valueOf(21)).get();
        dailyActivity.setIdAlumn(alumno);*/
        HttpSession session = request.getSession();
        Alumn alumn = (Alumn) session.getAttribute("alumno");
        if(alumn != null){
            dailyActivity.setIdAlumn((Alumn) alumn);
        }
        repositoryActivity.save(dailyActivity);
        return "redirect:/"+Long.valueOf( ((Alumn) alumn).getId());
    }

    /**
     * Muestra el formulario de inicio de sesión.
     *
     * @param model Modelo para la vista.
     * @return Vista del formulario de inicio de sesión.
     */
    @GetMapping("/login")
    public String getLogin(Model model){
        model.addAttribute("alumno",new Alumn());
        return "login";
    }

    /**
     * Verifica las credenciales del alumno al iniciar sesión.
     *
     * @param alumn  Alumno con las credenciales.
     * @param request Petición HTTP.
     * @return Redirige a la lista de actividades del alumno si las credenciales son correctas, de lo contrario, redirige al inicio de sesión.
     */
    @GetMapping("/succesfull")
    public String getAlumno(@ModelAttribute Alumn alumn, HttpServletRequest request){
        Boolean existence = repositoryAlumn.existsAlumnByDni(alumn.getDni());
        System.out.println(existence.toString());
        if(existence){
            Alumn alumnBBDD = repositoryAlumn.getAlumnByDni(alumn.getDni());
            if(alumnBBDD.getPassword().equals(alumn.getPassword())){
                HttpSession s=request.getSession();
                s.setAttribute("alumno",alumnBBDD);
                return "redirect:/"+Long.valueOf( alumnBBDD.getId());
            }else{
                return "redirect:/login";
            }
        }else{
            return "redirect:/login";
        }
    }

    /**
     * Procesa la solicitud para borrar una actividad.
     *
     * @param id     ID de la actividad a borrar.
     * @param request Objeto HttpServletRequest para acceder a la sesión.
     * @param model   Modelo para almacenar datos.
     * @return La redirección a la vista correspondiente.
     */
    @PostMapping("/borrarActividad/{id}")
    public String borrarActividad(@PathVariable Long id, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        Alumn alumn = (Alumn) session.getAttribute("alumno");
        if(alumn != null){
            DailyActivity dailyActivity = repositoryActivity.findById(id).get();
            repositoryActivity.delete(dailyActivity);
            return "redirect:/"+alumn.getId();
        }else{
            model.addAttribute("alumno", new Alumn());
            return "login";
        }
    }

    /**
     * Cierra la sesión del usuario.
     *
     * @param model   Modelo para almacenar datos.
     * @param request Objeto HttpServletRequest para acceder a la sesión.
     * @return La vista de inicio de sesión.
     */
    @GetMapping("logout")
    public String logout(Model model, HttpServletRequest request)  {
            HttpSession session = request.getSession();
                session.invalidate();
                model.addAttribute("alumno", new Alumn());
                return "login";
    }
}
