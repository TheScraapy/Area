const services = require('../config/about.json');

module.exports = {
    async config(req, res) {
        res.send({
            "client": {
                "host": req.connection.remoteAddress.split(":")[3]
            },
            "server": {
                "current_time": Date.now(),
                services
            }
        })
    }
}