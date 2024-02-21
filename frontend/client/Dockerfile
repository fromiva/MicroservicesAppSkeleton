FROM node:20.11-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY src/ ./src/
COPY *.json ./
RUN npm run build


FROM nginx:1.25-alpine AS client
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/dist/client /usr/share/nginx/html
