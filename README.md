## AREA

[Vue](https://vuejs.org/), [Express](https://expressjs.com/), [Node](https://nodejs.org/en/), [sqlite3](https://www.sqlite.org/index.html), Java

A full stack web project including mobile and browser support.
Based on [IFTTT](http://ifttt.com).

### Disclaimer

This project will only build on Linux systems and the mobile version is only available for Android.

### Steps to run :

1. Install [docker](https://www.docker.com/) and [docker-compose](https://docs.docker.com/compose/) on your computer.

2. Navigate into the directory DEV_area_2018
```
$> cd your/path/to/DEV_area_2018
```

3. Build Docker Images
```
$> sudo docker-compose build
```

4. Run the stack
```
$> sudo docker-compose up
```

Your app should be running on : http://localhost:8081
Your mobile app should be downloadable at : http://localhost:8081/client.apk

Now you just have to create an account, log in and enjoy !


### Presentation

There are 3 parts to this dockerized app: Frontend (Vue), Backend (Express) and Mobile (Java)

The frontend is in the **client/** folder,
The backend in the **server/** folder,
The application in the **mobile/** folder.

### Services

You will have access to multiple services in the app. Each one of them as actions and/or reactions and some can receive a parameter.
Service list includes : Youtube, Facebook, Twitter, Gmail, Google calendar and Clock.

### Warnings

If you run `npm install` locally in the server or client folder, you'll need to delete **node_modules/** before running the docker setup again.
```
$> rm -rf client/node_modules && rm -rf server/node_modules
```

If you run `./gradlew` locally make sure to delete the build files before running the docker setup again.

### Documentation guide

1. Full stack web documentation :
```ClientAndServer.pdf```

2. Android application documentation :
```MobileDoc.pdf```

3. Check out our [API documentation](https://documenter.getpostman.com/view/5512362/S11RKFiY) with Postman.

### License

This project is the property of EPITECH.