@echo off

TITLE Hashicorp Vault Server

REM Create data directory for raft storage backend to use
mkdir data

REM Set the vault server address
setx VAULT_ADDR https://localhost:8200

vault server -config=prod.hcl
