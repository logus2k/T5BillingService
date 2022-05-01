#!/bin/sh

# References:
## keytool - https://docs.oracle.com/en/java/javase/12/tools/keytool.html
## openssl - https://www.openssl.org/docs/man3.0/man1/


# SERVER KEY, CERTIFICATE, KEY STORE, AND TRUST STORE GENERATION

## 1. Generate a private RSA key and a X509 certificate, in one step:
openssl req -x509 -newkey rsa:4096 -sha256 -days 10950 -nodes -keyout tech5billing.com.CA.key -out tech5billing.com.CA.crt -subj '/CN=tech5billing.com' -addext 'subjectAltName=DNS:tech5billing.com,DNS:www.tech5billing.com,IP:127.0.0.1'

## 2. Create a PKCS12 keystore from the private key and the public certificate:
openssl pkcs12 -export -name tech5billing.com -in tech5billing.com.CA.crt -inkey tech5billing.com.CA.key -out tech5billing.com.keystore.p12 -passout pass:Tech5!

## 3. Convert a PKCS12 keystore into a JKS keystore:
keytool -importkeystore -srckeystore tech5billing.com.keystore.p12 -srcstoretype pkcs12 -srcstorepass Tech5! -destkeystore tech5billing.com.keystore.jks -deststoretype jks -deststorepass Tech5! -alias tech5billing.com

## 4. Import the server certificate to the server trust store:
keytool -import -alias tech5billing.com -file tech5billing.com.CA.crt -keystore tech5billing.com.truststore.p12 -storepass Tech5! -noprompt

## 5. Convert the PKCS12 truststore into a JKS truststore:
keytool -importkeystore -srckeystore tech5billing.com.truststore.p12 -srcstoretype pkcs12 -srcstorepass Tech5! -destkeystore tech5billing.com.truststore.jks -deststoretype jks -deststorepass Tech5! -alias tech5billing.com


## CLIENT KEY, CERTIFICATE, KEY STORE, AND TRUST STORE GENERATION

## 1. Generate a private RSA key and a X509 certificate, in one step:
openssl req -x509 -newkey rsa:4096 -sha256 -days 10950 -nodes -keyout client.tech5billing.com.CA.key -out client.tech5billing.com.CA.crt -subj '/CN=clienttech5billing.com' -addext 'subjectAltName=DNS:clienttech5billing.com,DNS:www.clienttech5billing.com,IP:127.0.0.1'

## 2. Create a PKCS12 keystore from the private key and the public certificate:
openssl pkcs12 -export -name clienttech5billing.com -in client.tech5billing.com.CA.crt -inkey client.tech5billing.com.CA.key -out client.tech5billing.com.keystore.p12 -passout pass:Tech5!

## 3. Convert a PKCS12 keystore into a JKS keystore:
keytool -importkeystore -srckeystore client.tech5billing.com.keystore.p12 -srcstoretype pkcs12 -srcstorepass Tech5! -destkeystore client.tech5billing.com.keystore.jks -deststoretype jks -deststorepass Tech5! -alias clienttech5billing.com

## 4. Import a server's certificate to the client's trust store.
keytool -import -alias tech5billing.com -file tech5billing.com.CA.crt -keystore client.tech5billing.com.truststore.jks -storepass Tech5! -noprompt

## 5. Import a client's certificate to the client's trust store.
keytool -import -alias client.tech5billing.com -file client.tech5billing.com.CA.crt -keystore client.tech5billing.com.truststore.jks -storepass Tech5! -noprompt

## 6. Import a client's certificate to the server's trust store.
keytool -import -alias client.tech5billing.com -file client.tech5billing.com.CA.crt -keystore tech5billing.com.truststore.jks -storepass Tech5! -noprompt
