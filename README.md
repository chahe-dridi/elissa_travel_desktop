Elissa Travel Desktop Application
Elissa Travel is a desktop application for managing travel-related activities, including flight reservations, event bookings, hotel reservations, and organized voyages. The application is developed using Java, JavaFX, and Scene Builder, with IntelliJ IDEA as the integrated development environment (IDE). It also features weather information integration using a Weather API and secure payment processing with Stripe.

Table of Contents
Installation
Usage
Features
Screenshots
Contributing
License
Contact
Installation
To get a local copy up and running, follow these steps:

Prerequisites
Java Development Kit (JDK) 11 or higher
IntelliJ IDEA
JavaFX SDK
Scene Builder
A modern operating system (Windows, macOS, Linux)
Steps
Clone the repository

sh
 
git clone  https://github.com/chahe-dridi/elissa_travel_desktop
Open IntelliJ IDEA

Open IntelliJ IDEA and select File > Open....
Navigate to the cloned repository directory and open it.
Set up JavaFX in IntelliJ IDEA

Go to File > Project Structure....
Select Modules and then Dependencies.
Click the + icon, select Library > From Maven..., and add the JavaFX library (e.g., org.openjfx:javafx-controls:11.0.2).
Configure JavaFX SDK

Download the JavaFX SDK from the Gluon website.
Unzip the downloaded file to a desired location.
In IntelliJ IDEA, go to File > Project Structure....
Select SDKs and click the + icon to add the JavaFX SDK.
Set up Scene Builder

Download and install Scene Builder from the Gluon website.
In IntelliJ IDEA, go to File > Settings... > Languages & Frameworks > JavaFX.
Set the path to the Scene Builder executable.
Run the application

Locate the main class file in the src directory (e.g., Main.java).
Right-click on the main class file and select Run 'Main'.
Usage
Once the application is running, you can access various features:

Flights Reservation
Search and book flights from a wide range of airlines.
View flight details and prices.
Manage bookings.
Events Booking
Browse and book tickets for various events.
View event details, dates, and prices.
Hotel Reservation
Search for hotels in your desired location.
View hotel details, availability, and prices.
Book rooms and manage reservations.
Organized Voyages
Explore and book organized travel packages.
View itinerary details and prices.
Weather Information
View current weather conditions for your travel destination.
Get weather forecasts to plan your trips better.
Secure Payments
Process payments securely using Stripe.
Manage payment details and history.
Admin Dashboard
View statistics related to site usage and bookings.
Manage user accounts and bookings.
Access an agenda to keep track of upcoming events and tasks.
Mailing System
Send booking confirmations and notifications to users.
Manage email templates.
Features
User Authentication: Secure sign-up and login functionality.
Responsive Design: User-friendly interface designed with JavaFX.
Interactive UI: Intuitive interface designed using Scene Builder.
Search Filters: Advanced search filters for flights, hotels, and events.
Weather Information: Integrated Weather API for travel destinations.
Secure Payments: Stripe payment integration for secure transactions.
Admin Dashboard: Detailed statistics and agenda for administrative tasks.
Mailing System: Automated emails for booking confirmations and notifications.
Screenshots
Main Dashboard
![image](https://github.com/chahe-dridi/elissa_travel_desktop/assets/91032735/3af075cd-1da7-4621-9660-c45d37ef7999)
![image](https://github.com/chahe-dridi/elissa_travel_desktop/assets/91032735/463c5e75-d405-43c5-b38f-5a8d9ba7508b)
![image](https://github.com/chahe-dridi/elissa_travel_desktop/assets/91032735/9e58b471-909c-44b6-88ee-733cf3fad811)

Flight Reservation
![image](https://github.com/chahe-dridi/elissa_travel_desktop/assets/91032735/71391296-c47f-44f6-993f-8845064fa93f)
![image](https://github.com/chahe-dridi/elissa_travel_desktop/assets/91032735/38a7bd6d-e2c3-4f2c-8444-c62b981f9e87)

Hotel Reservation
![image](https://github.com/chahe-dridi/elissa_travel_desktop/assets/91032735/8ae4fbe9-73d3-47e7-a185-c5a9affaaa78)

Admin Dashboard
![image](https://github.com/chahe-dridi/elissa_travel_desktop/assets/91032735/263bd2ad-99cb-4751-b215-f311b8517158)
![image](https://github.com/chahe-dridi/elissa_travel_desktop/assets/91032735/9430049b-d8d9-46b9-a88d-48931d44b90c)
![image](https://github.com/chahe-dridi/elissa_travel_desktop/assets/91032735/797edcde-91a0-4046-9081-3a26dd6ac780)
![image](https://github.com/chahe-dridi/elissa_travel_desktop/assets/91032735/a92a9474-456b-4dfb-be6e-6f3695d43772)
![image](https://github.com/chahe-dridi/elissa_travel_desktop/assets/91032735/feb5db9e-36a8-4448-81ca-f47e984b87c1)

Contributing
Contributions are what make the open-source community such an amazing place to be, learn, inspire, and create. Any contributions you make are greatly appreciated.

Fork the Project
Create your Feature Branch (git checkout -b feature/AmazingFeature)
Commit your Changes (git commit -m 'Add some AmazingFeature')
Push to the Branch (git push origin feature/AmazingFeature)
Open a Pull Request
License
Distributed under the MIT License. See LICENSE for more information.

Contact
Dridi Chaher - chaher.dridi@ieee.org

Project Link:  https://github.com/chahe-dridi/elissa_travel_desktop
