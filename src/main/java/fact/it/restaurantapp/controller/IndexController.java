package fact.it.restaurantapp.controller;

import fact.it.restaurantapp.betaling.NormaleBetaling;
import fact.it.restaurantapp.model.*;
import fact.it.restaurantapp.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

@Controller
public class IndexController {
    private PersoneelRepository personeelRepository;
    private GerechtRepository gerechtRepository;
    private BestellingRepository bestellingRepository;
    private TafelRepository tafelRepository;
    private BesteldItemRepository besteldItemRepository;

    public IndexController(PersoneelRepository personeelRepository, GerechtRepository gerechtRepository, BestellingRepository bestellingRepository, TafelRepository tafelRepository, BesteldItemRepository besteldItemRepository) {
        this.personeelRepository = personeelRepository;
        this.gerechtRepository = gerechtRepository;
        this.bestellingRepository = bestellingRepository;
        this.tafelRepository = tafelRepository;
        this.besteldItemRepository = besteldItemRepository;
    }

    private static double round(double value) {
        if (2 < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RedirectView postIndex(RedirectAttributes redirectAttrs) {
        voegDatabaseItemsToe();
        return new RedirectView("/");
    }

    private void addModelAttributes(Model model) {
        model.addAttribute("personeellijst", personeelRepository.findAll());
        model.addAttribute("tafellijst", tafelRepository.findAll());
        model.addAttribute("gerechtenlijst", gerechtRepository.findAll());
        model.addAttribute("bestellingenlijst", bestellingRepository.findAll());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex(Model model) {
        if (personeelRepository.findAll().size() > 1) {
            model.addAttribute("added", true);
            this.addModelAttributes(model);
        } else {
            model.addAttribute("added", false);
            model.addAttribute("personeellijstError", "De database is al opgevuld.");
        }
        model.addAttribute("title", "home");
        return "index";
    }

    private boolean voegDatabaseItemsToe() {
        List<Boolean> responses = new ArrayList<>();
        responses.add(addPersoneel());
        responses.add(addTafels());
        responses.add(addGerechten());
        responses.add(addBestellingen());
        return !responses.contains(false);
    }

    private boolean addPersoneel() {
        if (personeelRepository.findAll().size() > 4) return false;
        for (int i = 1; i < 6; i++) {
            Personeel zaal = new Zaalpersoneel();
            zaal.setNaam("ZP" + i);
            personeelRepository.save(zaal);
        }

        for (int i = 1; i < 6; i++) {
            Personeel keuken = new Keukenpersoneel();
            keuken.setNaam("KP" + i);
            personeelRepository.save(keuken);
        }
        return true;
    }

    private boolean addTafels() {
        if (tafelRepository.findAll().size() > 4) return false;
        for (int i = 1; i < 6; i++) {
            Tafel tafel = new Tafel();
            tafel.setCode("T0" + i);
            tafelRepository.save(tafel);
        }
        return true;
    }

    private boolean addGerechten() {
        if (gerechtRepository.findAll().size() > 4) return false;
        for (int i = 1; i < 6; i++) {
            Gerecht gerecht = new Gerecht();
            gerecht.setNaam("gerecht " + i);
            Random r = new Random();
            gerecht.setActuelePrijs(round(r.nextDouble() * 10));
            gerechtRepository.save(gerecht);
        }
        return true;
    }

    private boolean addBestellingen() {
        if (bestellingRepository.findAll().size() > 4) return false;
        List<Gerecht> gerechten = gerechtRepository.findAll();

        NormaleBetaling normaleBetaling = new NormaleBetaling();
        for (int i = 1; i < 6; i++) {
            Bestelling bestelling = new Bestelling();
            bestelling.setBetaalStrategie(normaleBetaling);
            bestelling.setDatum(new GregorianCalendar());
            Random r = new Random();
            for (int j = 0; j < i; j++)
                bestelling.addItem(gerechten.get(r.nextInt(5)), (r.nextInt(5)));

            bestelling.setTafel(tafelRepository.findAll().get(r.nextInt(5)));
            bestelling.setZaalpersoneel((Zaalpersoneel) personeelRepository.findAll().get(r.nextInt(5)));
            bestellingRepository.save(bestelling);

            for (BesteldItem item : bestelling.getBesteldItems())
                besteldItemRepository.save(item);
//            }
        }
        return true;
    }
}
