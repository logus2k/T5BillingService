Billing Server Certificates - Configuration Notes

---

openssl x509 -text -in 'C:\Labs\Git\t5billingv3\T5BillingService\src\main\resources\tech5billing.com.CA.crt' -noout
openssl x509 -subject -issuer -noout -in 'C:\Labs\Git\t5billingv3\T5BillingService\src\main\resources\tech5billing.com.CA.crt'


# Sample successful calls from curl:
&"C:\Program Files\curl\bin\curl.exe" -v --cacert 'C:\Labs\Git\interceptor\interceptor\src\main\keystore\server\tech5lds.crt.pem' 'https://tech5lds.com:9443/api_version'
&"C:\Program Files\curl\bin\curl.exe" -v --cacert 'C:\Labs\Git\t5billingv3\T5BillingService\src\main\resources\tech5billing.com.CA.crt' 'https://tech5billing.com:10443/version'

# Check keystore type:
keytool -list -keystore tech5billing.com.keystore.jks



# VERIFIED CERTIFICATES GENERATION STEPS

## SERVER

1. Generate a private key and a X509 certificate in one step:

openssl req -x509 -newkey rsa:4096 -sha256 -days 10950 -nodes -keyout tech5billing.com.CA.key -out tech5billing.com.CA.crt -subj '/CN=tech5billing.com' -addext 'subjectAltName=DNS:tech5billing.com,DNS:www.tech5billing.com,IP:127.0.0.1'


2. Create a PKCS12 keystore from private key and public certificate.

openssl pkcs12 -export -name tech5billing.com -in tech5billing.com.CA.crt -inkey tech5billing.com.CA.key -out tech5billing.com.keystore.p12 -passout pass:Tech5!


3. Convert a PKCS12 keystore into a JKS keystore

keytool -importkeystore -srckeystore tech5billing.com.keystore.p12 -srcstoretype pkcs12 -srcstorepass Tech5! -destkeystore tech5billing.com.keystore.jks -deststoretype jks -deststorepass Tech5! -alias tech5billing.com


4. Import a server's certificate to the server's trust store.

keytool -import -alias tech5billing.com -file tech5billing.com.CA.crt -keystore tech5billing.com.truststore.p12 -storepass Tech5! -noprompt


5. Convert the PKCS12 truststore into a JKS truststore

keytool -importkeystore -srckeystore tech5billing.com.truststore.p12 -srcstoretype pkcs12 -srcstorepass Tech5! -destkeystore tech5billing.com.truststore.jks -deststoretype jks -deststorepass Tech5! -alias tech5billing.com



## CLIENT





# Steps to create RSA key, self-signed certificates, keystore, and truststore for a server:

1. Generate a private RSA key

openssl genrsa -out tech5billing.com.CA.key 4096


2. Create a x509 certificate

openssl req -x509 -new -nodes -key tech5billing.com.CA.key -sha256 -days 10950 -out tech5billing.com.CA.pem

Country Name (2 letter code) [AU]:AU
State or Province Name (full name) [Some-State]:New South Wales
Locality Name (eg, city) []:Sydney
Organization Name (eg, company) [Internet Widgits Pty Ltd]:TECH5
Organizational Unit Name (eg, section) []:TECH5 AUSTRALIA PTY LTD
Common Name (e.g. server FQDN or YOUR name) []:tech5billing.com
Email Address []:sales@tech5-au.com


3. Create a PKCS12 keystore from private key and public certificate.

openssl pkcs12 -export -name tech5billing.com -in tech5billing.com.CA.pem -inkey tech5billing.com.CA.key -out tech5billing.com.keystore.p12


4. Convert PKCS12 keystore into a JKS keystore

keytool -importkeystore -destkeystore tech5billing.com.keystore.jks -srckeystore tech5billing.com.keystore.p12 -srcstoretype pkcs12 -alias tech5billing.com


5. Import a server's certificate to the server's trust store.

keytool -import -alias tech5billing.com -file tech5billing.com.CA.pem -keystore tech5billing.com.truststore.jks

Trust this certificate? [no]:  yes


# Steps to create RSA private key, self-signed certificate, keystore, and truststore for a client

1. Generate a private key

openssl genrsa -out client.tech5billing.com.CA.key 4096


2. Create a x509 certificate

openssl req -x509 -new -nodes -key client.tech5billing.com.CA.key -sha256 -days 10950 -out client.tech5billing.com.CA.pem

Country Name (2 letter code) [AU]:AU
State or Province Name (full name) [Some-State]:New South Wales
Locality Name (eg, city) []:Sydney
Organization Name (eg, company) [Internet Widgits Pty Ltd]:TECH5
Organizational Unit Name (eg, section) []:TECH5 AUSTRALIA PTY LTD
Common Name (e.g. server FQDN or YOUR name) []:client.tech5billing.com
Email Address []:sales@tech5-au.com


3. Create PKCS12 keystore from private key and public certificate.

openssl pkcs12 -export -name client.tech5billing.com -in client.tech5billing.com.CA.pem -inkey client.tech5billing.com.CA.key -out client.tech5billing.com.keystore.p12


4. Convert a PKCS12 keystore into a JKS keystore

keytool -importkeystore -destkeystore client.tech5billing.com.keystore.jks -srckeystore client.tech5billing.com.keystore.p12 -srcstoretype pkcs12 -alias client.tech5billing.com


5. Import a server's certificate to the client's trust store.

keytool -import -alias tech5billing.com -file tech5billing.com.CA.pem -keystore client.tech5billing.com.truststore.jks

Trust this certificate? [no]:  yes


6. Import a client's certificate to the client's trust store.

keytool -import -alias client.tech5billing.com -file client.tech5billing.com.CA.pem -keystore client.tech5billing.com.truststore.jks

Trust this certificate? [no]:  yes


5. Import a client's certificate to the server's trust store.

keytool -import -alias client.tech5billing.com -file client.tech5billing.com.CA.pem -keystore tech5billing.com.truststore.jks

Trust this certificate? [no]:  yes


OPTIONAL: openssl x509 -outform der -in tech5billing.com.CA.pem -out tech5billing.com.CA.cer

openssl x509 -outform der -in tech5billing.com.CA.pem -out tech5billing.com.CA.cer