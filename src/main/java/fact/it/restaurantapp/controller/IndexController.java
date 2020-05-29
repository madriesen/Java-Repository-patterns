package fact.it.restaurantapp.controller;

import fact.it.restaurantapp.betaling.NormaleBetaling;
import fact.it.restaurantapp.helpers.DoubleHelper;
import fact.it.restaurantapp.model.*;
import fact.it.restaurantapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

@Controller
public class IndexController {
    @Autowired
    private PersoneelRepository personeelRepository;
    @Autowired
    private GerechtRepository gerechtRepository;
    @Autowired
    private BestellingRepository bestellingRepository;
    @Autowired
    private TafelRepository tafelRepository;
    @Autowired
    private BesteldItemRepository besteldItemRepository;
    private int teller = 0;


    @PostMapping(value = "/")
    public RedirectView postIndex() {
        voegDatabaseItemsToe();
        return new RedirectView("/");
    }

    private void addModelAttributes(Model model) {
        model.addAttribute("personeellijst", personeelRepository.findAll());
        model.addAttribute("tafellijst", tafelRepository.findAll());
        model.addAttribute("gerechtenlijst", gerechtRepository.findAll());
        model.addAttribute("bestellingenlijst", bestellingRepository.findAll());
    }

    @GetMapping(value = "/")
    public String getIndex(Model model) {
        // voeg teller toe om 1x de toegevoegde items te laten zien.
        boolean eerste_keer = teller <= 1;
        model.addAttribute("first_time", eerste_keer);

        // er is nog niets toegevoegd dus toon knop om items toe te voegen
        model.addAttribute("added", false);

        // als er al iets toegevoegd is, verberg knop
        if (personeelRepository.findAll().size() > 1) {
            model.addAttribute("added", true);

            // als items al getoond zijn, toon niet nog een keer
            if (eerste_keer)
                this.addModelAttributes(model);
        }

        // verhoog de index teller als er al toegevoegd is.
        if (teller >= 1) teller++;


        // geef title mee
        model.addAttribute("title", "home");
        return "index";
    }

    private void voegDatabaseItemsToe() {
        // vermeerder teller wanneer items toegevoegd worden.
        teller++;
        addPersoneel();
        addTafels();
        addGerechten();
        addBestellingen();
    }

    private void addPersoneel() {
        // als er meer dan 4 personeelsleden zijn, vul niet meer op.
        if (personeelRepository.findAll().size() > 4) return;

        // voeg 5 Zaalpersoneelsleden toe
        for (int i = 1; i < 6; i++) {
            Personeel zaal = new Zaalpersoneel();
            zaal.setNaam("ZP" + i);
            personeelRepository.save(zaal);
        }

        // voeg 5 Keukenpersoneelsleden toe
        for (int i = 1; i < 6; i++) {
            Personeel keuken = new Keukenpersoneel();
            keuken.setNaam("KP" + i);
            personeelRepository.save(keuken);
        }
    }

    private void addTafels() {
        // als er meer dan 4 tafels zijn, vul niet meer op.
        if (tafelRepository.findAll().size() > 4) return;

        // voeg 5 tafels toe
        for (int i = 1; i < 6; i++) {
            Tafel tafel = new Tafel();
            tafel.setCode("T0" + i);
            tafelRepository.save(tafel);
        }
    }

    private void addGerechten() {
        // als er meer dan 4 gerechten zijn, vul niet meer op.
        if (gerechtRepository.findAll().size() > 4) return;

        // voeg 5 gerechten toe
        for (int i = 1; i < 6; i++) {
            Gerecht gerecht = new Gerecht();
            gerecht.setNaam("gerecht " + i);

            // genereer een random prijs -> r.nextDouble geeft waarde tussen 0 en 1 -> * 10 om prijs te hebben tussen 0 en 10
            Random r = new Random();
            gerecht.setActuelePrijs(DoubleHelper.round(r.nextDouble() * 10));
            gerechtRepository.save(gerecht);
        }
    }

    private void addBestellingen() {
        // als er meer dan 4 bestellingen zijn, vul niet meer op.
        if (bestellingRepository.findAll().size() > 4) return;

        // haal alle gerechten op
        List<Gerecht> gerechten = gerechtRepository.findAll();

        // set betaling naar normale betaling
        NormaleBetaling normaleBetaling = new NormaleBetaling();
        for (int i = 1; i < 6; i++) {
            Bestelling bestelling = new Bestelling();
            bestelling.setBetaalStrategie(normaleBetaling);
            bestelling.setDatum(new GregorianCalendar());

            // voeg random gerecht, random aantal keer teo aan de bestelling
            Random r = new Random();
            for (int j = 0; j < i; j++)
                bestelling.addItem(gerechten.get(r.nextInt(5)), (r.nextInt(5)));

            bestelling.setTafel(tafelRepository.findAll().get(r.nextInt(5)));
            bestelling.setZaalpersoneel((Zaalpersoneel) personeelRepository.findAll().get(r.nextInt(5)));
            bestellingRepository.save(bestelling);

            for (BesteldItem item : bestelling.getBesteldItems())
                besteldItemRepository.save(item);
        }
    }
}
