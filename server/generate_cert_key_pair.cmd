keytool -genkeypair -alias vault-cert -keyalg RSA -keysize 2048 -keystore vault-keystore.jks -dname "CN=vault.node, OU=IS, O=IBM Corp, L=Bengaluru, ST=Karnataka, C=IN" -storepass manage -validity 365

keytool -exportcert -alias vault-cert -keystore vault-keystore.jks -file vault-cert.crt -rfc -storepass manage

keytool -importkeystore -srckeystore vault-keystore.jks -srcstoretype JKS -destkeystore vault-keystore.p12 -deststoretype PKCS12 -srcalias vault-cert -srcstorepass manage -deststorepass manage

openssl pkcs12 -in vault-keystore.p12 -nocerts -nodes -out vault-key.pem -passin pass:manage

openssl pkcs12 -in vault-keystore.p12 -clcerts -nokeys -out vault-cert.pem -passin pass:manage
