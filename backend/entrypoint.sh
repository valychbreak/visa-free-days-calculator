#!/bin/bash

jdbc_url_param="-Ddatasources.default.url=${jdbc_url}"
jdbc_username_param="-Ddatasources.default.username=${jdbc_username}"
jdbc_password_param="-Ddatasources.default.password=${jdbc_password}"

# Optional JDBC url
if [ -z "$jdbc_url" ]; then
  unset jdbc_url_param
else
  echo "Overriding JDBC url to ${jdbc_url}"
fi

# Optional JDBC username
if [ -z "$jdbc_username" ]; then
  unset jdbc_username_param
else
  echo "Overriding JDBC username to ${jdbc_username}"
fi

# Optional JDBC password
if [ -z "$jdbc_password" ]; then
  unset jdbc_password_param
else
  echo "Overriding JDBC password to ***"
fi

echo "Launching application..."

java ${jdbc_url_param} \
  ${jdbc_username_param} \
  ${jdbc_password_param} \
  "-Dcom.sun.management.jmxremote" \
  "-Xmx128m" \
  -jar "$1"
