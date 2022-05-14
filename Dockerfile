FROM openjdk:8-jdk-alpine

COPY target/LoanApplicationToken_API-1.0.0.jar /tmp/

WORKDIR /tmp/

CMD ["java", "-jar", "LoanApplicationToken_API-1.0.0.jar"]

EXPOSE 8501
