package ml.bereket.githubresume.controller;

import ml.bereket.githubresume.dto.GithubProfile;
import ml.bereket.githubresume.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping
    public String getResume(@RequestParam("username") String username, Model model) {
        GithubProfile profile = resumeService.buildProfile(username);
        model.addAttribute("profile", profile);
        return "resume";
    }
}
