#Acquire mysql from Docker Hub, using latest version
FROM mysql/mysql-server:latest
#Copy the sql initialisation script into MySQL's startup folder
COPY world.sql /docker-entrypoint-initdb.d
#Set the root password for MySQL
ENV MYSQL_ROOT_PASSWORD example
#Mac Fix
ENV MYSQL_ROOT_HOST=%