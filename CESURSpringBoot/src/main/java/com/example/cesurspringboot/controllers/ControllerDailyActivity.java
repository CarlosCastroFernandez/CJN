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
     * Redirección a la ruta /login

     * @return retorna la redireccion a login
     */
    @GetMapping("/")
    public String devueltaLogin(){
        return "redirect:/login";
    }
    /**
     * Obtiene todas las actividades de un alumno por su ID.
     *
     * @param id      ID del alumno.
     * @param model   Modelo para la vista.
     * @param request Petición HTTP.
     * @return Vista con la lista de actividades del alumno o redirige al inicio de sesión.
     */
    @GetMapping("/{id}")
    public String getAllActivityByAlumnId(@PathVariable Long id,Model model,HttpServletRequest request){
        HttpSession session=request.getSession();
        Alumn alumnoSession=(Alumn)session.getAttribute("alumno");

        if(alumnoSession!=null){
            Alumn alumno=alumnoSession;
            model.addAttribute("actividades",repositoryActivity.getAllByIdAlumn(alumno));
            System.out.println(repositoryActivity.getAllByIdAlumn(alumno));
            return "getAllActivityByAlumnId";
        }else{
            model.addAttribute("alumno",new Alumn());
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
        HttpSession session=request.getSession();
        Alumn alumno=(Alumn) session.getAttribute("alumno");
        System.out.println(dailyActivity);

        if(alumno!=null&&(!(dailyActivity.getTaskName().equals(""))&&!(dailyActivity.getDate().equals("")))&&dailyActivity.getPracticeType()!=null&&dailyActivity.getTotalHours()!=null){
            System.out.println(dailyActivity);
            dailyActivity.setIdAlumn(alumno);
            repositoryActivity.save(dailyActivity);
            return "redirect:/"+Long.valueOf( dailyActivity.getIdAlumn().getId()) ;
        }else if(alumno==null){
            model.addAttribute("alumno",new Alumn());
            return "login";
        } else if (dailyActivity.getTaskName().equals("")||dailyActivity.getDate().equals("")||dailyActivity.getPracticeType()==null||dailyActivity.getTotalHours()==null) {
            return "redirect:/new";
        } else{
            return "";
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
        DailyActivity actividad=new DailyActivity();

        HttpSession session=request.getSession();
        Alumn alumno=(Alumn) session.getAttribute("alumno");
        if(alumno!=null){
            actividad.setIdAlumn(alumno);
            model.addAttribute("actividad",actividad);
            return "editar";
        }else{
            model.addAttribute("alumno",new Alumn());
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
    public String editDailyActivity(@PathVariable Long idActividad, Model model, HttpServletRequest request){
        HttpSession session=request.getSession();
        Alumn alumno= (Alumn) session.getAttribute("alumno");
        if(alumno==null){
            model.addAttribute("alumno",new Alumn());
            return "login";
        }else{

            model.addAttribute("actividad",repositoryActivity.findById(idActividad).get());
            return "editar";
        }

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
    public String editActivityPost(@PathVariable Long idActividad, @ModelAttribute DailyActivity dailyActivity,HttpServletRequest request,Model model){
       /* Alumn alumno=repositoryAlumn.findById(Long.valueOf(21)).get();
        dailyActivity.setIdAlumn(alumno);*/
        HttpSession sesion=request.getSession();
        Alumn alumno= (Alumn) sesion.getAttribute("alumno");
        if(alumno!=null&&!dailyActivity.getTaskName().equals("")&&!dailyActivity.getDate().equals("")&&dailyActivity.getTotalHours()!=null&&dailyActivity.getPracticeType()!=null){
            dailyActivity.setIdAlumn((Alumn) alumno);
            repositoryActivity.save(dailyActivity);
            return "redirect:/"+Long.valueOf( ((Alumn) alumno).getId());
        }else if(alumno==null){
            model.addAttribute("alumno",new Alumn());
            return "login";
        } else if(dailyActivity.getTaskName().equals("")||dailyActivity.getDate().equals("")||dailyActivity.getPracticeType()==null||dailyActivity.getTotalHours()==null){
            return "redirect:/edit/"+dailyActivity.getId();
        } else{
            return "";
        }

    }
    /**
     * Muestra el formulario de inicio de sesión.
     *
     * @param modelo Modelo para la vista.
     * @return Vista del formulario de inicio de sesión.
     */

    @GetMapping("/login")
    public String getLogin(Model modelo){
        modelo.addAttribute("alumno",new Alumn());
        return "login";
    }
    /**
     * Verifica las credenciales del alumno al iniciar sesión.
     *
     * @param alumno  Alumno con las credenciales.
     * @param request Petición HTTP.
     * @return Redirige a la lista de actividades del alumno si las credenciales son correctas, de lo contrario, redirige al inicio de sesión.
     */
    @GetMapping("/succesfull")
    public String getAlumno(@ModelAttribute Alumn alumno, HttpServletRequest request){
        Boolean existencia=repositoryAlumn.existsAlumnByDni(alumno.getDni());
        System.out.println(existencia.toString());
        if(existencia){
            Alumn alumnoBBDD=repositoryAlumn.getAlumnByDni(alumno.getDni());
            if(alumnoBBDD.getPassword().equals(alumno.getPassword())){
                HttpSession s=request.getSession();
                s.setAttribute("alumno",alumnoBBDD);
                return "redirect:/"+Long.valueOf( alumnoBBDD.getId());
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
    public String borrarActividad(@PathVariable Long id, HttpServletRequest request,Model model){
        HttpSession session=request.getSession();
        Alumn alumno= (Alumn) session.getAttribute("alumno");

        if(alumno!=null&&id!=null){
            DailyActivity actividad=repositoryActivity.findById(id).get();
            repositoryActivity.delete(actividad);
            return "redirect:/"+alumno.getId();
        } else if (alumno==null) {
            model.addAttribute("alumno",new Alumn());
            return "login";
        } else{
            DailyActivity actividad=new DailyActivity();
            actividad.setIdAlumn(alumno);
            model.addAttribute("actividad",actividad);
            return "editar";
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
            HttpSession session=request.getSession();
                session.invalidate();
                model.addAttribute("alumno",new Alumn());
                return "login";

    }


}
