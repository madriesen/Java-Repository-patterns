package fact.it.restaurantapp.controller;

import fact.it.restaurantapp.betaling.BetaalStrategie;
import fact.it.restaurantapp.betaling.HappyHourBetaling;
import fact.it.restaurantapp.betaling.NormaleBetaling;
import fact.it.restaurantapp.model.*;
import fact.it.restaurantapp.repositories.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

@Controller
public class BestellingController {
    @Autowired
    private BestellingRepository bestellingRepository;
    @Autowired
    private TafelRepository tafelRepository;
    @Autowired
    private PersoneelRepository personeelRepository;
    @Autowired
    private GerechtRepository gerechtRepository;
    @Autowired
    private BesteldItemRepository besteldItemRepository;
    private List<Bestelling> lijst = new ArrayList<>();
    private boolean search = false;


    @GetMapping("/bestellingen")
    public String retrieve(Model model) {
        if (!search) lijst = bestellingRepository.findAll();
        else model.addAttribute("zoekresultaten", true);

        search = false;

        model.addAttribute("bestellinglijst", lijst);
        model.addAttribute("title", "Bestellingen");
        return "crud/bestellingen";
    }

    @GetMapping("/bestellingen/clear")
    public String clear(Model model) {
        lijst.clear();
        return this.retrieve(model);
    }

    @GetMapping("/bestellingen/zoeken")
    public String showSearch(Model model) {
        model.addAttribute("title", "Zoeken in bestellingen");
        model.addAttribute("tafels", tafelRepository.findAll());
        return "search/bestellingen";

    }

    @PostMapping("/bestellingen/zoeken")
    public String search(Model model, @RequestParam(name = "action") String action, @RequestParam(required = false, name = "datum") String datum, @RequestParam(required = false, name = "tafel") String tafel, @RequestParam(required = false, name = "bedrag") Double bedrag) {
        lijst.clear();
        search = true;
        switch (action) {
            case "datum":
                if (datum == null) return "redirect:/bestellingen";
                this.zoekOpDatum(datum);
                break;
            case "tafel":
                if (tafel.equals("null")) return "redirect:/bestellingen";
                this.zoekOpTafel(Long.parseLong(tafel));
                break;
            case "bedrag":
                if (bedrag == null) return "redirect:/bestellingen";
                this.zoekOpBedrag(bedrag);
                break;
        }

        return this.retrieve(model);
    }

    @GetMapping("/bestelling/{id}")
    public String getBestelling(Model model, @PathVariable(name = "id") long id) {
        Bestelling bestelling = bestellingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));

        model.addAttribute("bestelling", bestelling);
        model.addAttribute("title", "bestelling details");
        return "crud/bestelling_detail";
    }

    @GetMapping("/besetlling/nieuw")
    public String showNieuweBestelling(Model model) {
        model.addAttribute("personeelslijst", personeelRepository.findByDtype("Zaalpersoneel"));
        model.addAttribute("tafellijst", tafelRepository.findAll());
        model.addAttribute("gerechten", gerechtRepository.findAll());
        return "crud/nieuweBestelling";
    }

    @PostMapping("/bestelling/nieuw")
    public String processNieuweBestelling(Model model, @RequestParam(name = "zaalpersoneel") String zaalpersoneelId, @RequestParam(name = "tafel") String tafelId, @RequestParam(name = "alleGerechten") String alleGerechten, @RequestParam(name = "happyHour", defaultValue = "false") boolean happyhour) throws JSONException {
        Bestelling nieuweBestelling = new Bestelling();
        nieuweBestelling.setDatum(new GregorianCalendar());
        nieuweBestelling.setBetaald(false);
        setPersoneel(zaalpersoneelId, nieuweBestelling);
        setTafel(tafelId, nieuweBestelling);
        setBetaalstrategie(happyhour, nieuweBestelling);
        besteldeItemsToevoegen(alleGerechten, nieuweBestelling);
        bestellingOpslaan(nieuweBestelling);

        return "redirect:/bestellingen";

    }

    @GetMapping("/bestelling/{id}/betalen")
    public String betalen(Model model, @PathVariable(name = "id") long id){
        Bestelling bestelling = bestellingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));

        bestelling.setBetaald(true);
        bestellingRepository.save(bestelling);
        return "redirect:/bestellingen";
    }


    private void setPersoneel(@RequestParam(name = "zaalpersoneel") String zaalpersoneelId, Bestelling nieuweBestelling) {
        Zaalpersoneel zaal = (Zaalpersoneel) personeelRepository.findById(Long.parseLong(zaalpersoneelId)).orElseThrow(() -> new EntityNotFoundException(zaalpersoneelId));
        nieuweBestelling.setZaalpersoneel(zaal);
    }

    private void setTafel(@RequestParam(name = "tafel") String tafelId, Bestelling nieuweBestelling) {
        Tafel tafel = tafelRepository.findById(Long.parseLong(tafelId)).orElseThrow(() -> new EntityNotFoundException(tafelId));
        nieuweBestelling.setTafel(tafel);
    }

    private void setBetaalstrategie(@RequestParam(name = "happyHour", defaultValue = "false") boolean happyhour, Bestelling nieuweBestelling) {
        BetaalStrategie betaalStrategie = (happyhour) ? new HappyHourBetaling() : new NormaleBetaling();
        nieuweBestelling.setBetaalStrategie(betaalStrategie);
    }

    private void besteldeItemsToevoegen(@RequestParam(name = "alleGerechten") String alleGerechten, Bestelling nieuweBestelling) throws JSONException {
        JSONObject gerechtenAmounts = new JSONObject(alleGerechten);
        Iterator<String> keys = gerechtenAmounts.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            Gerecht gerecht = gerechtRepository.findById(Long.parseLong(key)).orElseThrow(() -> new EntityNotFoundException(key));
            nieuweBestelling.addItem(gerecht, gerechtenAmounts.getInt(key));
        }
    }

    private void bestellingOpslaan(Bestelling nieuweBestelling) {
        bestellingRepository.save(nieuweBestelling);
        for (BesteldItem item : nieuweBestelling.getBesteldItems())
            besteldItemRepository.save(item);
    }

    private void zoekOpBedrag(Double bedrag) {

        for (Bestelling bestelling : bestellingRepository.findAll()) {
            if (bestelling.getTotaal() >= bedrag)
                lijst.add(bestelling);
        }
    }

    private void zoekOpTafel(Long tafelId) {
        Tafel tafel = tafelRepository.findById(tafelId).orElseThrow(() -> new EntityNotFoundException(Long.toString(tafelId)));
        lijst = bestellingRepository.findByTafel(tafel);
    }

    private void zoekOpDatum(String datum) {

        for (Bestelling bestelling : bestellingRepository.findAll()) {
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