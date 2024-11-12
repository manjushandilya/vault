@echo off

title Unseal Server

REM Initialize Vault
vault operator init -tls-skip-verify

REM Every initialized Vault server starts in the sealed state. 
REM From the configuration, Vault can access the physical storage, 
REM but it can't read any of it because it doesn't know how to decrypt it. 
REM The process of teaching Vault how to decrypt the data is known as unsealing the Vault.

REM Begin unsealing the Vault
REM To unseal the vault you must use three different unseal keys
vault operator unseal -tls-skip-verify
vault operator unseal -tls-skip-verify
vault operator unseal -tls-skip-verify

REM authenticate as the initial root token which was 
REM included in the output with the unseal keys
vault login -tls-skip-verify

REM Enable the AppRole auth method
vault auth enable -tls-skip-verify approle
