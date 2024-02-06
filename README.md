# Blood Donation System, Distributed Systems Assignment 2023-2024
This project is part of the winter semester for the course of Distributed Systems of Harokopio University of Athens.

The project is composed of two parts: the backend application and the frontend application.

## Installation instructions:

### 1. Running the backend.
The application uses a PostgreSQL database contained in Docker. To run the database use the following:

```docker run --name DS_Blood_Donations -e POSTGRES_PASSWORD=Kondocker123!@# -e POSTGRES_USER=postgres -e POSTGRES_DB=DS_Blood_Donations -p 5432:5432 -d postgres```

**Database connection details:**

-Port: 5432

-Username: postgres

-Password: Kondocker123!@#

### 2. Running application:
Simply clone and open the project in your favourite IDE (preferably Intellij). Please, make sure to wait for all dependencies to be downloaded first. Finally, run the application server in the IDE.



