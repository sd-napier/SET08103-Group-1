# gets latest image for mysql server from docker
FROM mysql/mysql-server:latest
# Copies the .sql database file and recreates it upon creating of the container
COPY world.sql /docker-entrypoint-initdb.d
# Sets root password for MySql so we can use it to connect and authorise
ENV MYSQL_ROOT_PASSWORD example
# Mac Fix for environment
ENV MYSQL_ROOT_HOST=%