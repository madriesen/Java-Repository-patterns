package fact.it.restaurantapp.model;

import fact.it.restaurantapp.observer.Observer;
import fact.it.restaurantapp.observer.Subject;

import java.util.ArrayList;

public class IngangTeller implements Subject {

    private static IngangTeller ingangTeller;
    protected ArrayList<Observer> observers = new ArrayList<>();
    private int aantal = 0;

    private IngangTeller() {
        System.out.println("Er wordt een singleton-object aangemaakt.");
    }

    public static IngangTeller getInstance() {
        if (ingangTeller == null)
            ingangTeller = new IngangTeller();

        return ingangTeller;
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
        this.notifyObserver();
    }


    @Override
    public void attachObserver(Observer newObserver) {
        observers.add(newObserver);
    }

    @Override
    public void detachObserver(Observer deleteObserver) {
        int observerIndex = observers.indexOf(deleteObserver);
        observers.remove(observerIndex);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}

