const { Area } = require('../models');

module.exports = {
    async updateService(id, lastData, timeout = 20) {
        Area.update({
            lastTime: Date.now(),
            lastData: lastData,
            timeout: Date.now() + (timeout * 1000)
        },
            { where: { id } }
        );
    }
}