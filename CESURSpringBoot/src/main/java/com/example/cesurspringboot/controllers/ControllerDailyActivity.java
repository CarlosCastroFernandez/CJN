package com.example.cesurspringboot.controllers;

import com.example.cesurspringboot.SessionService;
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

import java.util.List;

@Controller
@RequestMapping("/cesurapp")
public class ControllerDailyActivity {
    @Autowired
    private RepositoryDailyActivity repositoryActivity;

    @Autowired
    private RepositoryAlumn repositoryAlumn;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/{id}")
    public String getAllActivityByAlumnId(@PathVariable Long id,Model model){

        Alumn alumno=repositoryAlumn.findById(id).get();
        model.addAttribute("actividades",repositoryActivity.getAllByIdAlumn(alumno));
        return "getAllActivityByAlumnId";
    }

    @PostMapping("/new")
    public String newActividad(@ModelAttribute DailyActivity dailyActivity){
        Alumn alumno=repositoryAlumn.findById(Long.valueOf(21)).get();
        dailyActivity.setIdAlumn(alumno);
        System.out.println(dailyActivity);
        repositoryActivity.save(dailyActivity);
        return "redirect:/cesurapp/"+Long.valueOf( dailyActivity.getIdAlumn().getId()) ;
    }
    @GetMapping("/new")
    public String newActividad(Model model){
        DailyActivity actividad=new DailyActivity();
        actividad.setIdAlumn( sessionService.alumnoActivo());

        model.addAttribute("actividad",actividad);
        return "editar";
    }
    @GetMapping("/edit/{idActividad}")
    public String editDailyActivity(@PathVariable Long idActividad, Model model){
        model.addAttribute("actividad",repositoryActivity.findById(idActividad).get());
        return "editar";
    }
    @PostMapping("/edit/{idActividad}")
    public String editActivityPost(@PathVariable Long idActividad, @ModelAttribute DailyActivity dailyActivity,HttpServletRequest request){
        Alumn alumno=repositoryAlumn.findById(Long.valueOf(21)).get();
        dailyActivity.setIdAlumn(alumno);
        /*Object login=request.getAttribute("alumno");
        if(login!=null){
            dailyActivity.setIdAlumn((Alumn) login);
        }*/
        repositoryActivity.save(dailyActivity);
        return "redirect:/cesurapp/"+Long.valueOf( dailyActivity.getIdAlumn().getId());
    }

    @GetMapping("/login")
    public String getLogin(Model modelo){
        modelo.addAttribute("alumno",new Alumn());
        return "login";
    }
    @GetMapping("/succesfull")
    public String getAlumno(@ModelAttribute Alumn alumno, HttpServletRequest request){
        Boolean existencia=repositoryAlumn.existsAlumnByEmail(alumno.getEmail());
        if(existencia){
            Alumn alumnoBBDD=repositoryAlumn.getAlumnByEmail(alumno.getEmail());
            if(alumnoBBDD.getPassword().equals(alumno.getPassword())){
                HttpSession s=request.getSession();
                s.setAttribute("alumno",alumnoBBDD);
                return "redirect:/cesurapp/"+Long.valueOf( alumnoBBDD.getId());
            }else{
                return "redirect:/cesurapp/login";
            }
        }else{
            return "redirect:/cesurapp/login";
        }
    }

}
