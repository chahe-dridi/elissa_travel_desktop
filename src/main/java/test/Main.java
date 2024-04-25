package test;

import models.Event;
import models.ReservationEvent;
import models.TypeEvenement;
import services.ServiceEvent;
import services.ServiceReservationEvent;
import services.ServiceTypeEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class Main {

    public static void main(String[] args) {

        ServiceTypeEvent sE=new ServiceTypeEvent();
       //****Ajouter**/
        //TypeEvenement typeEvent=new TypeEvenement("zzzz","zzzzzzhhh");
     //   sE.ajouterTypeEvenement(typeEvent);

        //***Affichage**/
      // System.out.println(sE.afficherTypeEvenement());



        //*** Modifier***/
      //  TypeEvenement typeEvent=new TypeEvenement(11,"uuuuu","rrrrr");
//sE.modifierTypeEvenement(typeEvent);

        //**** supprimer***/
      //  sE.supprimerTypeEvenement(11);
       /* --------------------------------------------------*/
        //*** Service Event *****//

        //1-Ajouter
        ServiceEvent sEvent=new ServiceEvent();
        // Define date format
      /*  SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // Parse date strings
            Date startDate = dateFormat.parse("10/10/2001");
            Date endDate = dateFormat.parse("14/10/2001");
            // Create Event object
            Event E = new Event("aa", "tunis", 4, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()), 40, "image.png", 10, 1);
            // Add the event to the database
            sEvent.ajouterEvent(E);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // Parse date strings
            Date startDate = dateFormat.parse("10/10/2001");
            Date endDate = dateFormat.parse("14/10/2001");
            // Create Event object
            Event Ea = new Event("hello", "tunisie", 4, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()), 40, "image.png", 10, 1);
            // Add the event to the database
            sEvent.ajouterEvent(Ea);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//2-Modifier
     /*   SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Parse date strings
            Date startDate = dateFormat.parse("10/10/2001");
            Date endDate = dateFormat.parse("14/10/2001");
            // Create Event object
            Event E = new Event(15,"aa", "tunis", 4, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()), 40, "image.png", 10, 1);
            // Add the event to the database
            sEvent.modifierEvent(E);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
/////////////////////////////////////////supprimerevent////////////////////////////////////////////////
       // sEvent.supprimerEvent(21);

        ///////////////////////////serviceReservationEvent////////////////////////////////////////////
        /////////////////////////ajouter///////////////////////////////////////////////////////////
        ServiceReservationEvent r=new ServiceReservationEvent();

       /* ReservationEvent reservationEvent=new ReservationEvent(1,15);

        r.ajouterReservationEvent(reservationEvent);

        ReservationEvent reservationEvent2=new ReservationEvent(1,18);
        r.ajouterReservationEvent(reservationEvent2);


        */

        /////////////////////////////////////////////////modifier///////////////////////////////






    }

    }

