# vault
<br>
This is a pilot for (a) Vault dev server (b) Vault agent - a client-side daemon and (c) Vault java client for managing secrets<br>
<br>
- The server/start.cmd starts the server in dev mode<br>
<br>
- The agent start.cmd does the following:<br>
&nbsp;&nbsp;1> Enable the AppRole auth method.<br>
&nbsp;&nbsp;2> Enable the transit secrets engine.<br>
&nbsp;&nbsp;3> Create an encryption key ring.<br>
&nbsp;&nbsp;4> Create a policy by supplying a config file.<br>
&nbsp;&nbsp;5> Create a role with policy created in previous step without requiring secret id and configure blocks of IP addresses which can perform the login operation.<br>
&nbsp;&nbsp;6> Persist the RoleID to a file.<br>
&nbsp;&nbsp;7 Write a secret to the kv store.<br>
&nbsp;&nbsp;8> Start the agent by supplying a config file.<br>
&nbsp;&nbsp;9> The agent in-turn does the following:<br>
&nbsp;&nbsp;&nbsp;&nbsp;- Uses auto-auth and writes token to file sink.<br>
&nbsp;&nbsp;&nbsp;&nbsp;- Turns on caching for tokens.<br>
&nbsp;&nbsp;&nbsp;&nbsp;- Auto updates tokens on expiry using listener.<br>
<br>
- The java client has sample code written using the vault http api to manage secrets and use two way encryption.<br>
