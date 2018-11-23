FROM httpd:latest
LABEL maintainer="GrzegorzNowak"
RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install -y apache2
RUN apt-get install -y apache2-utils
EXPOSE 80
CMD ["apache2ctl", "-D", "FOREGROUND"]
