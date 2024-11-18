@echo off

TITLE Hashicorp Vault Development Server

setx SAG_IS_CONFIG_PROPERTIES %CD%\application.properties

setx VAULT_CACERT %CD%\vault-ca.pem

vault server -dev -dev-listen-address=localhost:8200 -dev-tls -dev-tls-cert-dir=%CD% -dev-root-token-id=manage
