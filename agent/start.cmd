set VAULT_ADDR=http://localhost:8200

vault auth enable approle

vault policy write my-role-policy policy.cnf 

vault write auth/approle/role/my-role token_policies="my-role-policy"

vault read -field=role_id auth/approle/role/my-role/role-id > role_id_file

vault write -field=secret_id -f auth/approle/role/my-role/secret-id > secret_id_file

vault kv put secret/integrationserver foo=bar

vault agent -config=config.hcl -log-level=debug
