set VAULT_ADDR=http://localhost:8200

vault auth enable approle

vault policy write esbrole-policy policy.cnf 

vault write auth/approle/role/esbrole token_policies="esbrole-policy" ^
bind_secret_id=false secret_id_bound_cidrs="0.0.0.0/0"

vault read -field=role_id auth/approle/role/esbrole/role-id > role_id_file

REM vault write -field=secret_id -f auth/approle/role/esbrole/secret-id > secret_id_file

vault kv put secret/esb foo=bar

vault agent -config=config.hcl -log-level=debug
