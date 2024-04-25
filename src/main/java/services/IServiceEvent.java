package services;

import models.Event;
import java.util.List;

public interface IServiceEvent {

    public void ajouterEvent(Event event);

    public List<Event> afficherEvent();

    public void modifierEvent(Event event);

    public void supprimerEvent(int id);
}
