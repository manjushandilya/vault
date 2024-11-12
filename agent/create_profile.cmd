@echo off

type policy_esb.cnf | powershell -Command "$input | ForEach-Object { $_ -replace \"esb\", \"%1\" }" > policy_%1.cnf
type start_prod_esb.cmd | powershell -Command "$input | ForEach-Object { $_ -replace \"esb\", \"%1\" }" > start_prod_%1.cmd
type config_esb.hcl | powershell -Command "$input | ForEach-Object { $_ -replace \"esb\", \"%1\" }" > config_%1.hcl
type config_esb.hcl | powershell -Command "$input | ForEach-Object { $_ -replace \"8002\", \"%2\" }" > config_%1.hcl
