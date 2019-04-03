FROM node:latest
RUN npm install -g http-server
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 8081
CMD cp /shared/client.apk ./static/client.apk; \ 
		npm run build; \
		http-server dist -p 8081
