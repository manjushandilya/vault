set VAULT_ADDR=http://localhost:8200
vault write -format=json -wrap-ttl=60s -f auth/approle/myapp/secret-id | jq -r .wrap_info.token > VAULT_TOKEN
REM SECRET_ID=$(vault unwrap -format=json ${VAULT_TOKEN} | jq -r .data.secret_id) &&
REM export VAULT_TOKEN=$(vault write -format=json auth/approle/login role_id=$ROLE_ID secret_id=$SECRET_ID | jq -r .auth.client_token)