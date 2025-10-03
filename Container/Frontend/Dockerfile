# Usa la imagen base de Nginx
FROM nginx:alpine

# Copia la carpeta Fronted al contendor
COPY Frontend/html /usr/share/nginx/html/
COPY Frontend/css /usr/share/nginx/html/
COPY Frontend/js /usr/share/nginx/html/

# Copia la configuracion personalizada de Nginx
COPY Container/Frontend/nginx.conf /etc/nginx/conf.d/default.conf

# Expone el puerto 80
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
