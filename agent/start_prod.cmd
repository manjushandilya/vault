@echo off

title Agent - Hashicorp Vault

REM Check for the status
vault status -tls-skip-verify

REM authenticate as the initial root token which was 
REM included in the output with the unseal keys
vault login -tls-skip-verify

REM Enable the transit secrets engine 
vault secrets enable -tls-skip-verify transit

REM Create an encryption key named esb
vault write -tls-skip-verify -f transit/keys/esb

REM Create a policy
vault policy write -tls-skip-verify esbrole-policy policy.cnf

REM Create a role with specified policy and CIDR
vault write -tls-skip-verify auth/approle/role/esbrole token_policies="esbrole-policy" ^
	bind_secret_id=false secret_id_bound_cidrs="0.0.0.0/0"

REM Retreive role id and write it to a file
vault read -tls-skip-verify -field=role_id auth/approle/role/esbrole/role-id > role_id_file

REM Uncomment the following if a separate secret id needs to be provisioned
REM and also set bind_secret_id=true and remove secret_id_bound_cidrs
REM vault write -tls-skip-verify -field=secret_id -f auth/approle/role/esbrole/secret-id > secret_id_file

REM Enable kv2 at esb
vault secrets enable -tls-skip-verify -version=2 -path secret kv

REM Write a secret key-value pair
vault kv put -tls-skip-verify -mount=secret esb foo=bar hello=world
REM vault kv put -tls-skip-verify secret/esb foo=bar hello=world

REM Read a secret key-value pair
REM vault kv get -tls-skip-verify -field=foo secret/esb

REM Start vault agent with config.hcl as configuration in debug mode
vault agent -tls-skip-verify -config=config.hcl -log-level=debug
