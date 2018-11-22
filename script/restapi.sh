#!/bin/bash

export TKN=$(curl -X POST "http://localhost:8080/auth/realms/master/protocol/openid-connect/token" \
                  -d "client_id=demo-client" \
                  -d "username=siu" \
                  -d "password=siu" \
                  -d "grant_type=password" \
                  -d "client_secret=9ceb02f6-08a4-4143-b0fa-870c6b8860c1" \
                  | jq -r ".access_token")

curl -X GET "http://localhost:8081/test" \
     -H "Accept: application/json" \
     -H "Authorization: Bearer $TKN"