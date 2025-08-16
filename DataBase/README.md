# Database

* Aunque se va utilizar el ORM de SpringBoot JPA implementacion Hibernate. Es buena practica comprobar el correcto disñeo de la DB en base a los requerimientos.

    ## PosgreSQL

    * La base de datos elegida es postgreSQL 
    * Los pasos a seguir para crear la base de datos son los siguientes

        ### Pasos 

        * **Ingresar a PosgreSQL**

            ```bash
                sudo -u postgres psql
            ```
        * **Creacion de un usuario**

            ```sql
                CREATE USER solus WITH PASSWORD '123456';
            ```
        *  **Creacion de un rol**

            ```sql
                CREATE ROLE admin;
            ```
        * **Asignar permisos a un rol**

            ```sql
                ALTER ROLE admin CREATEDB;
            ```

        * **Asignar rol a usuario**

            ```sql
                GRANT admin TO solus;
            ```
        * **Crear Base de datos**

            ```sql
                CREATE DATABASE note_app OWNER solus;
            ```
        * **Ingresar con usuario Definido**

            ```sql
                psql -h localhost -p 5432 -U "solus" -d note_app;
            ```
