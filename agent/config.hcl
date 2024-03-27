# Example Vault Agent configuration file with file sink, AppRole authentication, and caching

pid_file = "./agent.pid"

vault {
  address = "http://localhost:8200"
}

auto_auth {
  method "approle" {
    mount_path = "auth/approle"
    config = {
      role_id_file_path = "role_id_file"
      secret_id_file_path = "secret_id_file"
	  remove_secret_id_file_after_reading = true
    }
  }
  
  sink "file" {
    #wrap_ttl = "24h"
    config {
	  path = "token_file"
	}
	#dh_type = "curve25519"
	#dh_path = "/"
	#derive_key = true
	#aad = "aes256-gcm96"
  }
}

cache {
  use_auto_auth_token = true
  enable_caching = true
  cache_size = 1000
  max_ttl = "5m"
}

listener "tcp" {
  address = "localhost:8002"
  tls_disable = true
}

exit_after_auth = false
