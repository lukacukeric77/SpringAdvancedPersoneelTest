package be.vdab.personeel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller @RequestMapping("/")
class IndexController {

    private String welcome(){
        return "index";
    }

}
