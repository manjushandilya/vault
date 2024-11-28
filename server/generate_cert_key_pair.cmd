keytool -genkeypair -alias vault-prod-cert -keyalg RSA -keysize 2048 -keystore vault-prod-keystore.jks -dname "CN=vault.node, OU=IS, O=IBM Corp, L=Bengaluru, ST=Karnataka, C=IN" -storepass manage -validity 365 -ext SAN=dns:vault.node,dns:localhost,ip:127.0.0.1

keytool -exportcert -alias vault-prod-cert -keystore vault-prod-keystore.jks -file vault-prod-cert.crt -rfc -storepass manage

keytool -importkeystore -srckeystore vault-prod-keystore.jks -srcstoretype JKS -destkeystore vault-prod-keystore.p12 -deststoretype PKCS12 -srcalias vault-prod-cert -srcstorepass manage -deststorepass manage

openssl pkcs12 -in vault-prod-keystore.p12 -nocerts -nodes -out vault-prod-key.pem -passin pass:manage

openssl pkcs12 -in vault-prod-keystore.p12 -clcerts -nokeys -out vault-prod-cert.pem -passin pass:manage
