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

@Controller
@RequestMapping("/")
public class ControllerDailyActivity {
    @Autowired
    private RepositoryDailyActivity repositoryActivity;

    @Autowired
    private RepositoryAlumn repositoryAlumn;


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

    @GetMapping("/login")
    public String getLogin(Model modelo){
        modelo.addAttribute("alumno",new Alumn());
        return "login";
    }
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
    @GetMapping("logout")
    public String logout(Model model, HttpServletRequest request)  {
            HttpSession session=request.getSession();
                session.invalidate();
                model.addAttribute("alumno",new Alumn());
                return "login";

    }


}
