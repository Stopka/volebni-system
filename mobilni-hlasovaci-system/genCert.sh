#!/bin/bash
mkdir certs
openssl req -new -newkey rsa:1024 -nodes -subj "/CN=VotingPointDefault" -keyout delete.key -out delete.rq
openssl x509 -req -days 365 -in delete.rq -signkey delete.key -out delete.crt
openssl pkcs12 -export -inkey delete.key -in delete.crt -password pass:12345 -out certs/server.p12
rm delete.*
rm .rnd
exit



:failed   
echo Please install OpenSSL, or add it into your path variable. Please visit http://www.openssl.org/ for information
