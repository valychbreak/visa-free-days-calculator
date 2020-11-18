## Stage 1 : NPM build produces built UI application
#FROM node:13.12.0-alpine as build-frontend
#
#WORKDIR /app
#ENV PATH /app/node_modules/.bin:$PATH
#
#COPY frontend/package.json ./
#COPY frontend/package-lock.json ./
#RUN npm ci
#RUN npm install react-scripts@3.4.1 -g --silent
#
#COPY ./frontend .
#
#RUN npm run build

FROM nginx:1.17
COPY build/ /usr/share/nginx/html