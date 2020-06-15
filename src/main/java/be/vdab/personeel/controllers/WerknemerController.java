package be.vdab.personeel.controllers;

import be.vdab.personeel.domain.Werknemer;
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

    public WerknemerController(JpaWerknemerServices werknemerServices) {
        this.werknemerServices = werknemerServices;
    }



    @GetMapping
    public ModelAndView ceoDisplay() {
        return processor(werknemerServices.findCeo());

    }

    @GetMapping("{id}")
    public ModelAndView werknemerDisplay(@PathVariable long id) {
        return processor(werknemerServices.findById(id));
    }



    private ModelAndView processor(Optional<Werknemer> optionalWerknemer) {
        ModelAndView modelAndView = new ModelAndView("werknemer");
        optionalWerknemer.ifPresent(werknemer -> {
            modelAndView.addObject(werknemer);
            modelAndView.addObject("ondergeschikten", werknemerServices.findByChefId(werknemer.getId()));
        });
        return modelAndView;
    }


}
