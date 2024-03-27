# vault

This project is a pilot for connecting to vault server using vault-agent (a client-side daemon)

1> Enable AppRole auth method
2> Create a policy by supplying a config file
3> Create a role with policy created in previous step without requiring secret id and configure blocks of IP addresses which can perform the login operation.
4> Persist the RoleID to a file
5> Write a secret to the kv store
6> Start the agent by supplying a config file
