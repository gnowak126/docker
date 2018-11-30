FROM java:8
LABEL maintainer="GrzegorzNowak"
COPY . /
WORKDIR /  
RUN javac DockerConnectToMySQL.java
CMD ["java", "-classpath", "mysql-connector-java-5.1.6.jar:.","DockerConnectToMySQL"]
