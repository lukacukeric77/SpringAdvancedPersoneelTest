package be.vdab.personeel.controllers;

import be.vdab.personeel.domain.Werknemer;
import be.vdab.personeel.services.JpaJobtitelServices;
import be.vdab.personeel.services.JpaWerknemerServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("werknemer")
public class WerknemerController {

    private final JpaWerknemerServices werknemerServices;
    private final JpaJobtitelServices jobtitelServices;

    public WerknemerController(JpaWerknemerServices werknemerServices, JpaJobtitelServices jobtitelServices) {
        this.werknemerServices = werknemerServices;
        this.jobtitelServices = jobtitelServices;
    }

    @GetMapping
    public ModelAndView ceoDisplay(){
        ModelAndView modelAndView = new ModelAndView("werknemer");
        Optional<Werknemer> optionalWerknemer = werknemerServices.findCeo();
        optionalWerknemer.ifPresent(werknemer -> {
            modelAndView.addObject("werknemer", werknemer);
            modelAndView.addObject("ondergeschikten", werknemerServices.findByChefId(werknemer.getId()));
        });
        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView werknemerDisplay(@PathVariable long id){
        ModelAndView modelAndView = new ModelAndView("werknemer");
        Optional<Werknemer> optionalWerknemer = werknemerServices.findById(id);
        optionalWerknemer.ifPresent(werknemer -> {
            modelAndView.addObject("werknemer", werknemer);
            modelAndView.addObject("ondergeschikten", werknemerServices.findByChefId(werknemer.getId()));
        });
        return modelAndView;
    }

}
