# build the ng project
FROM node:latest as node
WORKDIR /app

COPY . .

RUN npm config set proxy http://proxy.intra.bca.co.id:8080
RUN npm config set https-proxy http://proxy.intra.bca.co.id:8080
# RUN npm config set https_proxy http://proxy.intra.bca.co.id:8080
RUN npm install
# RUN npm run build --prod
RUN npm run build

# enable run on nginx
FROM nginx:alpine
COPY --from=node /app/dist/frontend /usr/share/nginx/html
