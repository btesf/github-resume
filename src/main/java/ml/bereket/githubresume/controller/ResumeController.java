package ml.bereket.githubresume.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResumeController {

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping
    public String getResume(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);
        return "resume";
    }
}
