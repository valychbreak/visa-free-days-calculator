# NOTE: this is not being used for production build

## Stage 1 : NPM build produces built UI application
FROM node:13.12.0-alpine as build-frontend

WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH

COPY package.json ./
COPY package-lock.json ./
RUN npm ci
RUN npm install react-scripts@3.4.1 -g --silent

COPY . .

RUN npm run build

FROM nginx:1.17
COPY --from=build-frontend /app/build /usr/share/nginx/html
