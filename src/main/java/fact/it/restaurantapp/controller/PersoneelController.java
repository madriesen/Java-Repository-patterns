package fact.it.restaurantapp.controller;

import fact.it.restaurantapp.model.Keukenpersoneel;
import fact.it.restaurantapp.model.Personeel;
import fact.it.restaurantapp.model.Zaalpersoneel;
import fact.it.restaurantapp.repositories.PersoneelRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
public class PersoneelController {
    private PersoneelRepository personeelRepository;

    public PersoneelController(PersoneelRepository personeelRepository) {
        this.personeelRepository = personeelRepository;
    }

    @GetMapping("/personeel")
    public String retrieve(Model model) {
        List<Personeel> personeellijst = personeelRepository.findAll();
        model.addAttribute("personeellijst", personeellijst);
        model.addAttribute("title", "Personeel");
        return "crud/personeel";
    }

    @PostMapping("/personeel/{id}")
    public String update(Model model, @PathVariable(name = "id") long personeelId, @RequestParam(name = "naam") String personeelNaam, @RequestParam(value = "action", required = true) String action) {
        System.out.println("request: id: " + personeelId + "; naam: " + personeelNaam + "; action: " + action);
        Personeel personeel = personeelRepository.findById(personeelId).orElseThrow(() -> new EntityNotFoundException(personeelNaam));
        if (action.equals("save"))
            this.savePersoneel(personeelNaam, personeel);
        else if (action.equals("delete"))
            System.out.println("kan geen personeelsleden verwijderen die al bestellingen hebben gedaan.");
//            personeelRepository.delete(personeel);
        return "redirect:/personeel/";
    }

    @PostMapping("/personeel/add")
    public String insert(Model model, @RequestParam(name = "dtype") String dtype, @RequestParam(name = "naam") String personeelNaam, @RequestParam(value = "action", required = true) String action) {
        System.out.println("request: dtype: " + dtype + "; naam: " + personeelNaam + "; action: " + action);
        if (action.equals("save")) {
            Personeel personeel;
            if (dtype.equals("zaal")) personeel = new Zaalpersoneel();
            else personeel = new Keukenpersoneel();
            this.savePersoneel(personeelNaam, personeel);
        }
        return "redirect:/personeel/";
    }

    private void savePersoneel(@RequestParam(name = "naam") String personeelNaam, Personeel personeel) {
        personeel.setNaam(personeelNaam);
        personeelRepository.save(personeel);
    }
}
