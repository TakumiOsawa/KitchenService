#Dockerfile for sample service using embedded tomcat server.

FROM centos:centos7
MAINTAINER Takumi.Osawa

RUN yum install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel \
       wget tar iproute git

RUN wget https://services.gradle.org/distributions/gradle-7.1-bin.zip
RUN yum install -y unzip
RUN unzip -d /opt/gradle ./gradle-7.1-bin.zip
ENV JAVA_HOME /etc/alternatives/jre
ENV GRADLE_HOME /opt/gradle/gradle-7.1
ENV PATH ${GRADLE_HOME}/bin:${PATH}
RUN git clone https://github.com/TakumiOsawa/KitchenService.git /var/local/ftgo/kitchen-service
WORKDIR /var/local/ftgo/kitchen-service
RUN gradle bootJar

RUN cp /etc/localtime /etc/localtime.org
RUN ln -sf  /usr/share/zoneinfo/Asia/Tokyo /etc/localtime

EXPOSE 8009

CMD java -jar -Dspring.profiles.active=production /var/local/ftgo/kitchen-service/build/libs/KitchenService-0.0.1-SNAPSHOT.jar