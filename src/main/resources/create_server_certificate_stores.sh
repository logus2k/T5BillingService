#!/bin/sh

# SERVER KEY, CERTIFICATE, KEY STORE, AND TRUST STORE GENERATION COMMANDS

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
