package fact.it.restaurantapp.controller;

import fact.it.restaurantapp.model.Bestelling;
import fact.it.restaurantapp.repository.BestellingRepository;
import fact.it.restaurantapp.repository.TafelRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BestellingController {
    private BestellingRepository bestellingRepository;
    private TafelRepository tafelRepository;
    private List<Bestelling> lijst = new ArrayList<>();

    public BestellingController(BestellingRepository bestellingRepository, TafelRepository tafelRepository) {
        this.bestellingRepository = bestellingRepository;
        this.tafelRepository = tafelRepository;
    }

    @GetMapping("/bestellingen")
    public String retrieve(Model model) {
        if (lijst.isEmpty()) lijst = bestellingRepository.findAll();
        else model.addAttribute("zoekresultaten", true);

        model.addAttribute("bestellinglijst", lijst);
        model.addAttribute("title", "Bestellingen");
        return "crud/bestellingen";
    }

    @GetMapping("/bestellingen/clear")
    public String clear(Model model) {
        lijst.clear();
        return this.retrieve(model);
    }

    @PostMapping("/bestellingen/{id}")
    public String update(Model model, @PathVariable(name = "id") long personeelId, @RequestParam(name = "naam") String personeelNaam, @RequestParam(value = "action", required = true) String action) {
        Bestelling bestelling = bestellingRepository.findById(personeelId).orElseThrow(() -> new EntityNotFoundException(personeelNaam));
        if (action.equals("save"))
            bestellingRepository.save(bestelling);
        else if (action.equals("delete"))
            bestellingRepository.delete(bestelling);
        return "redirect:/bestellingen/";
    }

    @GetMapping("/bestellingen/zoeken")
    public String showSearch(Model model) {
        model.addAttribute("title", "Zoeken in bestellingen");
        model.addAttribute("tafels", tafelRepository.findAll());
        return "search/bestellingen";

    }

    @PostMapping("/bestellingen/zoeken")
    public String search(Model model, @RequestParam(name = "action") String action, @RequestParam(required = false, name = "datum") String datum, @RequestParam(required = false, name = "tafel") Long tafel, @RequestParam(required = false, name = "bedrag") Double bedrag) {
        lijst.clear();
        switch (action) {
            case "datum":
                this.zoekOpDatum(datum);
                break;
            case "tafel":
                this.zoekOpTafel(tafel);
                break;
            case "bedrag":
                this.zoekOpBedrag(bedrag);
                break;
        }

        return this.retrieve(model);
    }

    private void zoekOpBedrag(Double bedrag) {
        List<Bestelling> alleBestellingen = bestellingRepository.findAll();

        for (Bestelling bestelling : alleBestellingen) {
            if (bestelling.getTotaal() >= bedrag)
                lijst.add(bestelling);
        }
    }

    private void zoekOpTafel(Long tafel) {
        List<Bestelling> alleBestellingen = bestellingRepository.findAll();
        for (Bestelling bestelling : alleBestellingen) {
            if (bestelling.getTafel().getTafelId().equals(tafel))
                lijst.add(bestelling);
        }
    }

    private void zoekOpDatum(String datum) {
        List<Bestelling> alleBestellingen = bestellingRepository.findAll();

        for (Bestelling bestelling : alleBestellingen) {
            if (bestelling.getFormattedDatum().equals(datum))
                lijst.add(bestelling);
        }
    }

}


//    @PostMapping("/bestellingen/add")
//    public String insert(Model model, @RequestParam(name = "dtype") String dtype, @RequestParam(name = "naam") String personeelNaam, @RequestParam(value = "action", required = true) String action) {
//        System.out.println("request: dtype: " + dtype + "; naam: " + personeelNaam + "; action: " + action);
//        if (action.equals("save")) {
//            this.saveBestelling(personeelNaam, new Bestelling());
//        }
//        return "redirect:/bestellingen/";
//    }