<img src="media\readme\LOGO_3.png" alt="good&cheap logo" width="60" height="60" align="right">

# MUBISOFT ECO

## Table of Contents

- [G&C, Keep It Fresh!](#intro)
- [Sustainable Development Goals](#sdg)
- [Local Server Setup](#local)
- [Application Requirements](#requirements)

<h2 id="intro">G&C, Keep It Fresh!</h2>

G&C (Good & Cheap) is a web application with the objective of ensuring sustainable consumption and production patterns in our cities. Reducing food waste at the retail and consumer level is the main focus of this application.

The application will provide users with a marketplace where shops around them will publish products close to their expiration date, at a cheaper price. Users will have the ability to buy these products and reduce the food waste around their community.

The application will also provide different recipes for the user to elaborate using the products they have bought. These recipes will provide a way of taking advantage of those products and reduce the waste of food at retail.

Check our amazing introductory video!
[![G&C introductory video](/media/readme/video_thumbnail.png)](https://youtu.be/noULh-zgVd0)

<h2 id="sdg">Sustainable Development Goals</h2>

Food loss and waste has indeed become an issue of great public concern. The 2030 Agenda for Sustainable Development reflects the increased global awareness of the problem. [Target 12.3](https://www.fao.org/sustainable-development-goals/goals/goal-12/en/) of the Sustainable Development Goals calls for halving per capita global food waste at retail and consumer levels by 2030, as well as reducing food losses along the production and supply chains.

![Sustainable Development Goal 12: Responsible COnsumption and production. Ensure sustainable consumption and production patters.](/media/readme/SDG12.jpg)

Due to this project nature, it is directly aligned with the Responsible Production and Consumption goal, to be more precise, with the Ensure Sustainable Consumption and Production Patterns.

The main objective of the mubisoft team, regarding of this projects, is to work to achive sustainable consumption, reducing wasted products at retail level.

<h2 id="local">Local Server Setup</h2>

In order to run the application as a local server in your computer, first of all, you have to make sure you have MySQL installed on your computer, you have a user with the name _mubisoft_admmin_ and a password _admin@mubisoft_ and you have created a database called _mubisoft_eco_, where the user has access.

Before running any of the applications, the _aplication.properties_ file must be modified to specify that the database is located in the computer that is being executed. The change must be the following:

```java
    // From:
    spring.datasource.url=jdbc:mysql://10.128.0.3:3306/mubisoft_eco
    
    // To:
    spring.datasource.url=jdbc:mysql://localhost:3306/mubisoft_eco
```

Once the properties file has been modified, the next step is to generate the database. If you want an empty database, just execute de G&C aplication. If you want to generate the database with content inside of it, the Generator aplication must be first executed. Just execute the Generate aplication and search 
[localhost/generate](http://localhost:8080/generate). The database will be generated, to follow the process check the server console.

With or without the the data generated on the database, if you want to try the aplication just run the G&C server and go to [index](http://localhost).

<h2 id="requirements">Requirements</h2>

The web application can be runned on the latest versions of the following web browsers:

| | Chrome | Firefox | Internet Explorer | Opera | Safari |
| - | - | - | - | - | - |
| Android | Supported | Supported | N/A | Not Supported | N/A |
| iOS | Supported | N/A | N/A | Not Supported | Supported |
| Mac OS X | Supported | Supported | N/A | Supported | Supported |
| Windows | Supported | Supported | Supported | Supported | Not Supported |

### Component Versions

- Java 1.8
- MySQL 8.0.27

### Credits

The web application was developed and tested by a team of students from [Mondragon Faculty of Engineering](https://www.mondragon.edu/en/faculty-of-engineering). The members of the team are:

- Oier Baños.
- Oihane Lameirinhas.
- Estalyn Curay.
- Joseba Izaguirre.
- Jon Navaridas.
