package com.example.pidev.services;

import com.example.pidev.entities.TypeEvenement;
import java.util.List;

public interface IServiceTypeEvenement{
    public void ajouterTypeEvenement(TypeEvenement TypeEvent);

    public List<TypeEvenement> afficherTypeEvenement();

    public void modifierTypeEvenement(TypeEvenement TypeEvent);

    public void supprimerTypeEvenement(int id);
}
