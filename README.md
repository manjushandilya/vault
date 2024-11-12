# vault
<br>
This is a pilot for (a) Vault dev/prod server (b) Vault agent(s) - client-side daemon and (c) Vault java client for managing secrets<br>
<br>
- The server/start_dev.cmd starts the server in dev mode<br>
- The server/start_prod_<profile>.cmd starts the server in dev mode<br>
- Both modes use self-signed certificates for TLS.<br>
<br>
- The agent/start_dev.cmd has commands with comments to start the vault server in development mode. Data will be stored in-memory only.<br>
<br>
- The agent/start_prod_<profile>.cmd has commands with comments to start the vault server in persisent mode. Data will be stored inside data folder.<br>
<br>
- New profiles can be created by invoking create_profile.cmd and passing profile name and listener port: Eg: create_profile.cmd profile 8003
- The java client has sample code written using the vault http api to manage secrets and use two way encryption.<br>
