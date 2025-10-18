# Despliegue

* La app esta enpaquetada en contenedores docker y se genero un archivo `docker-compose.yml` para facilitar el despliegue.

    * **Comandos para desplegar**

        ```bash
            cd NoteAPP/Container
            docker compose up --build
        ```
    * **Comando para listar contenedores activos**

        ```bash
            docker ps 
        ```
    * **Comando para eliminar contenedores levantados**

        ```bash
            docker compose down 
        ```

    * **Comando para eliminar contenedores y volumenes**

        ```bash
            docker compose down --volumes
      ```

    ## Dockerfile: `backend`

    * El Dockerfile del backend esta dividido en dos procesos build a Run para no tener archivos innecesarios en el contenedor

        ```Dockerfile
            # Stage 1: Builder

            FROM openjdk:21-jdk AS build

            WORKDIR /app

            COPY Backend/NoteApp/  ./

            RUN chmod +x mvnw
            RUN ./mvnw clean package -DskipTests

            # Stage 2: Runtime

            FROM openjdk:21-jdk

            COPY --from=build  /app/target/*.jar ./app.jar

            EXPOSE 8080 

            ENTRYPOINT ["java", "-jar", "/app.jar" ]
        ```
    ## Dockerfile: `Frontend`

    *  El frontend esta hecho sin ningun framework por lo cual se usa nginx para desplegar el (`html`, `css` y `js`).

        ### Archivo de configuracion de Nginx

        ```nginx
        server {
            listen 80;
            server_name localhost;

            root /usr/share/nginx/html;
            index html/index.html;

            # Ruta principal
            location / {
                try_files $uri $uri/ /html/index.html =404;
            }

            # Rutas para los assets estáticos
            location /css/ {
                alias /usr/share/nginx/html/css/;
            }

            location /js/ {
                alias /usr/share/nginx/html/js/;
            }
        }
        ```
        * Configura la ubicacion y acceso a las rutas estaticas de los directorios que contiene: (`html`, `css` y `js`).

        ### Dockerfile

        ```dockerfile
            # Usa la imagen base de Nginx
            FROM nginx:alpine

            # Elimina los archivos predeterminados de Nginx
            RUN rm -rf /usr/share/nginx/html/*

            # Copia la carpeta Fronted al contendor
            COPY Frontend/html /usr/share/nginx/html/html/
            COPY Frontend/css /usr/share/nginx/html/css/
            COPY Frontend/js /usr/share/nginx/html/js/

            # Copia la configuracion personalizada de Nginx
            COPY Container/Frontend/nginx.conf /etc/nginx/conf.d/default.conf

            # Expone el puerto 80
            EXPOSE 80

            CMD ["nginx", "-g", "daemon off;"]
        ```

    ## Docker-compose

    * Archivo que despliega la **db**, **backend** y **frontend** a partir de las imagenes generadas por el `Dockerfile` para (backend y frontend). Para la db se usa la imagen de **DockerHub** de postgreSQL.

        ```yml
            services:
        
            backend:
                build:
                context: ..
                dockerfile: Container/Backend/Dockerfile
                ports:
                - "8081:8080"
                environment:
                - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/note_app
                - SPRING_DATASOURCE_USERNAME=Solus
                - SPRING_DATASOURCE_PASSWORD=123456
                depends_on:
                - db
                networks:
                - note_network

            frontend:
                build:
                context:  ..
                dockerfile: Container/Frontend/Dockerfile
                ports:
                - "8080:80"
                depends_on:
                - backend
                networks:
                - note_network

            db:
                image: postgres:14
                environment:
                - POSTGRES_DB=note_app
                - POSTGRES_USER=Solus
                - POSTGRES_PASSWORD=123456
                ports:
                - "5433:5432"
                volumes:
                - postgres-data-note-app:/var/lib/postgresql/data
                networks:
                - note_network

            volumes:
            postgres-data-note-app:

            networks:
            note_network:
                driver: bridge
        ```
