@echo off

TITLE Hashicorp Vault Development Agent

REM Enable the AppRole auth method
vault auth enable approle

REM Enable the transit secrets engine 
vault secrets enable transit

REM Create an encryption key named esb
vault write -f transit/keys/esb

REM Create a policy
vault policy write esbrole-policy policy.cnf 

REM Create a role with specified policy and CIDR
vault write auth/approle/role/esbrole token_policies="esbrole-policy" ^
	bind_secret_id=false secret_id_bound_cidrs="0.0.0.0/0"

REM Retreive role id and write it to a file
vault read -field=role_id auth/approle/role/esbrole/role-id > role_id_file

REM Uncomment the following if a separate secret id needs to be provisioned
REM and also set bind_secret_id=true and remove secret_id_bound_cidrs
REM vault write -field=secret_id -f auth/approle/role/esbrole/secret-id > secret_id_file

REM Write a secret key-value pair
vault kv put secret/esb foo=bar hello=world

REM Read a secret key-value pair
REM vault kv get -field=foo secret/esb

REM Start vault agent with config.hcl as configuration in debug mode
vault agent -config=config.hcl -log-level=debug
