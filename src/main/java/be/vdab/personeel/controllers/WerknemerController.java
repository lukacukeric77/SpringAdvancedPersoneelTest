package be.vdab.personeel.controllers;

import be.vdab.personeel.domain.Werknemer;
import be.vdab.personeel.forms.OpslagForm;
import be.vdab.personeel.forms.RijksregisternummerForm;
import be.vdab.personeel.services.JpaWerknemerServices;
import be.vdab.personeel.sessions.Identificatie;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("werknemer")
public class WerknemerController {

    private final JpaWerknemerServices werknemerServices;
    private final Identificatie identificatie;

    public WerknemerController(JpaWerknemerServices werknemerServices, Identificatie identificatie) {
        this.werknemerServices = werknemerServices;
        this.identificatie = identificatie;
    }

    @GetMapping
    public ModelAndView ceoDisplay() {
        return processor(werknemerServices.findCeo());

    }

    @GetMapping("{id}")
    public ModelAndView werknemerDisplay(@PathVariable long id) {
        return processor(werknemerServices.findById(id));
    }


    //opslag

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
        werknemerServices.opslag(werknemer.getId(), form.getBedrag());
        attributes.addAttribute("id", werknemer.getId());
        return new ModelAndView("redirect:/werknemer/{id}");

    }

    //rijksretisternummer

    @GetMapping("{id}/rijksregisternummer")
    public ModelAndView rijksregisternummer(@PathVariable long id){
        ModelAndView modelAndView = new ModelAndView("rijksregisternummer");
        werknemerServices.findById(id)
                .ifPresent(werknemer -> {
                    identificatie.setId(werknemer.getId());
                    modelAndView.addObject(werknemer).addObject(new RijksregisternummerForm(werknemer.getRijksregisternr()));}
        );
        return modelAndView;
    }

    @PostMapping("{id}/rijksregisternummer")
    public ModelAndView rijksregisterNummer(@PathVariable long id,
                                            @Valid RijksregisternummerForm form,
                                            Errors errors,
                                            RedirectAttributes attributes){
        Optional<Werknemer> optionalWerknemer = werknemerServices.findById(id);
        if (!optionalWerknemer.isPresent()){
            return new ModelAndView("rijksregisternummer");
        }
        Werknemer werknemer = optionalWerknemer.get();
        if (errors.hasErrors()){
            return new ModelAndView("rijksregisternummer").addObject(werknemer);
        }
        werknemerServices.wijzigRijksregisternummer(werknemer.getId(), form.getRijksregisternummer());
        attributes.addAttribute("id", werknemer.getId());
        return new ModelAndView("redirect:/werknemer/{id}");
    }

    //secret methods

    private ModelAndView processor(Optional<Werknemer> optionalWerknemer) {
        ModelAndView modelAndView = new ModelAndView("werknemer");
        optionalWerknemer.ifPresent(modelAndView::addObject);
        return modelAndView;
    }


}
