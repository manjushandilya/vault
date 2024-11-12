storage "raft" {
  path    = "data"
  node_id = "vault.node"
}

listener "tcp" {
  address       = "127.0.0.1:8200"
  tls_disable   = "false"
  tls_cert_file = "vault-cert.pem"
  tls_key_file  = "vault-key.pem"
}

api_addr = "http://127.0.0.1:8200"
cluster_addr = "https://127.0.0.1:8201"
ui = true
disable_mlock = true
disable_sealwrap = true