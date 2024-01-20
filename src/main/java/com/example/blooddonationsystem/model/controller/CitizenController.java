
package com.example.blooddonationsystem.model.controller;

import com.example.blooddonationsystem.model.entity.Citizen;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("citizens")
public class  CitizenController {
    private List<Citizen> citizens = new ArrayList<Citizen>();

    @PostConstruct
    public void setup() {
        Citizen ctzn1 = new Citizen(1, "Alex", "Alexi", 24,"1234", "alexalexi@hua.gr", "AD1+", "A+");
        Citizen ctzn2 = new Citizen(2, "Dina", "Diamanti", 24, "123456789", "dinadiam@hua.gr","AD2","B+");
        citizens.add(ctzn1);
        citizens.add(ctzn2);
    }

    @GetMapping("/details")
    public String showCitizen(Model model) {
        model.addAttribute("citizens", citizens);
        return "citizens";
    }

    @GetMapping("{id}")
    public String showCitizen(@PathVariable Integer id, Model model) {
        Citizen ctzn = null;
        for (Citizen c : citizens) {
            if (c.getCitizenID().equals(id)) {
                ctzn = c;
                break;
            }
        }
        if (ctzn != null) {
            List<Citizen> citizenList = new ArrayList<>();
            citizenList.add(ctzn);
            model.addAttribute("citizens", citizenList);
        }
        return "citizens";
    }

    @GetMapping("delete/{id}")
    public String deleteCitizen(@PathVariable Integer id, Model model) {
        citizens.removeIf(citizen -> citizen.getCitizenID().equals(id));
        model.addAttribute("citizens", citizens);
        return "citizens";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        Citizen citizen = new Citizen();
        model.addAttribute("citizens", new Citizen());
        return "register";
    }

    @PostMapping("/register")
    public String showRegistrationForm(@ModelAttribute("citizen") Citizen citizen, Model model) {
        citizens.add(citizen);
        model.addAttribute("citizens", citizens);
        return "redirect:/citizens";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginCitizen(@RequestParam Integer id, Model model) {
        for (Citizen citizen : citizens) {
            if (citizen.getCitizenID().equals(id)) {
                model.addAttribute("citizen", citizen);
                return "dashboard";
            }
        }
        model.addAttribute("error", "No citizen found with that ID");
        return "login";
    }
}