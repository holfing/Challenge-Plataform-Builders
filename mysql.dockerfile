FROM mysql:8.0.18
MAINTAINER TIAGO COSTA DOS SANTOS<tiagocostasantos@gmail.com>
ENV MYSQL_ROOT_PASSWORD=123456
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=123456
ADD ./banco.sql /docker-entrypoint-initdb.d
ADD schema.sql /docker-entrypoint-initdb.d/schema.sql
EXPOSE 3306
CMD ["mysqld"]