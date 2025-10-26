FROM nginx:alpine

# Remove a configuração default
RUN rm /etc/nginx/conf.d/default.conf

# Copia a configuração do Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copia todos os arquivos do frontend (HTML, JS, CSS, imagens) para o Nginx
COPY . /usr/share/nginx/html/

EXPOSE 8080

CMD ["nginx", "-g", "daemon off;"]
