@echo off

setx SAG_IS_CONFIG_PROPERTIES %CD%\application.properties

vault server -dev -dev-listen-address=localhost:8200
