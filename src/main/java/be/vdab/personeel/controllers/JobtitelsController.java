package be.vdab.personeel.controllers;

import be.vdab.personeel.services.JobtitelServices;
import be.vdab.personeel.services.WerknemerServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("jobtitels")
class JobtitelsController {

    private final JobtitelServices jobtitelServices;
    private final WerknemerServices werknemerServices;

    public JobtitelsController(JobtitelServices jobtitelServices, WerknemerServices werknemerServices) {
        this.jobtitelServices = jobtitelServices;
        this.werknemerServices = werknemerServices;
    }

    @GetMapping
    public ModelAndView showTitles(){
        return new ModelAndView("jobtitels", "jobtitels", jobtitelServices.findAll());
    }

    @GetMapping("{id}")
    public ModelAndView showNames(@PathVariable long id){
        return new ModelAndView("jobtitels", "jobtitels", jobtitelServices.findAll())
                .addObject("workers", werknemerServices.findByJobtitelId(id))
                .addObject("title", jobtitelServices.findById(id).get());
    }

}
