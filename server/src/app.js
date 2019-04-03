const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const morgan = require('morgan');
const { sequelize } = require('./models');
const config = require('./config/config');
const http = require('http');
const AreaController = require('./controllers/AreaController');

// Http-server
const app = express();
const server = http.Server(app);

// Middlewares
app.use(morgan('dev'));
app.use(bodyParser.json());
app.use(cors());

// Routes
require('./routes')(app);

// DB & Server
sequelize.sync().then(() => {
    server.listen(config.port);
    console.log(`Server running on : localhost:${config.port}`);
    AreaController.areaRoutine();
});
