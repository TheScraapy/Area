const SequelizeController = require('../controllers/SequelizeController');

module.exports = {
    /* Actions */
    async everyDayAt(widget, callback) {
        if (widget.lastData == 'Time is ticking ...') {
            callback(widget, 'Time is up!');
        }
        const nextHour = parseInt(widget.actionData);
        var nextTime = nextHour ? nextHour : 12;
    	if (nextTime < 0 || nextTime > 23)
	    	nextTime = 12;
        var nextNoon = new Date();
        nextNoon.setHours(nextTime, 0, 0, 0);
        if (nextNoon.getTime() < Date.now())
            nextNoon.setDate(nextNoon.getDate() + 1);
        SequelizeController.updateService(widget.id, 'Time is ticking ...', Math.round((nextNoon - Date.now()) / 1000));
    },
    async everyHourAt(widget, callback) {
        if (widget.lastData == 'Time is ticking ...') {
            callback(widget, 'Time is up!');
        }
        const nextMin = parseInt(widget.actionData);
	    var nextTime = nextMin ? nextMin : 0;
	    if (nextTime < 0 || nextTime > 59)
		    nextTime = 0;
        var nextNoon = new Date();
        nextNoon.setHours(nextNoon.getHours(), nextTime, 0, 0);
        if (nextNoon.getTime() < Date.now())
            nextNoon.setHours(nextNoon.getHours() + 1);
        SequelizeController.updateService(widget.id, 'Time is ticking ...', Math.round((nextNoon - Date.now()) / 1000));
    }
    /* Reactions */
}