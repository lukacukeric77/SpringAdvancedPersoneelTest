package be.vdab.personeel.controllers;

import be.vdab.personeel.domain.Werknemer;
import be.vdab.personeel.forms.OpslagForm;
import be.vdab.personeel.services.JpaWerknemerServices;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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


    @GetMapping("{id}/opslag")
    public ModelAndView opslag(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("opslag");
        werknemerServices.findById(id).ifPresent(werknemer -> modelAndView.addObject(werknemer)
                .addObject(new OpslagForm(null)));
        return modelAndView;
    }

    @PostMapping("{id}/opslag")
    public ModelAndView opSlag(@PathVariable long id,
                               @Valid OpslagForm form,
                               Errors errors,
                               RedirectAttributes attributes) {
        Optional<Werknemer> optionalWerknemer = werknemerServices.findById(id);
        if (!optionalWerknemer.isPresent()) {
            return new ModelAndView("opslag");
        }
        Werknemer werknemer = optionalWerknemer.get();
        if (errors.hasErrors()) {
            return new ModelAndView("opslag").addObject(werknemer);
        }
        werknemer.opslag(form.getBedrag());
        attributes.addAttribute("id", werknemer.getId());
        return new ModelAndView("redirect:/werknemer/{id}");

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
