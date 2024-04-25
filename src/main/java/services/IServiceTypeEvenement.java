package services;

import models.TypeEvenement;

import java.lang.reflect.Type;
import java.util.List;

public interface IServiceTypeEvenement{
    public void ajouterTypeEvenement(TypeEvenement TypeEvent);

    public List<TypeEvenement> afficherTypeEvenement();

    public void modifierTypeEvenement(TypeEvenement TypeEvent);

    public void supprimerTypeEvenement(int id);
}
