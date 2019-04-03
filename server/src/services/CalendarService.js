const fetch = require('node-fetch');
const SequelizeController = require('../controllers/SequelizeController');

module.exports = {
    /* Actions */
    async detectEvent(widget, callback) {
        console.log('Checking for events...');
        
        const today = new Date().toISOString();
        fetch('https://www.googleapis.com/calendar/v3/calendars/primary/events?orderBy=startTime&singleEvents=true&timeMin=' + today, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + widget.serviceInToken
            }
        })
        .then(res => res.json())
        .then(result => {
            const events = result.items;

            if (events == null) {
                console.log('No event found.');
                return;
            }
            
            const nextEvent = events[0];
	        const eventStart = Date.parse(nextEvent.start.dateTime) / 1000;
            const now = Date.now() / 1000;
            const eventName = nextEvent.summary;
            const eventID = nextEvent.id;

            if (widget.lastData != null && eventID == widget.lastData) {
                console.log('No new event found.');
                return;
            }
            if ((eventStart - now) <= 3600)
                callback(widget, 'Your event ' + eventName + ' is in less than an hour!');
            SequelizeController.updateService(widget.id, eventID);
        })
        .catch(function (error) {
            console.log(error);
        });
    },
    /* Reactions */
    async createEvent(widget, callback) {
        console.log('Creating Calendar event');

        var startHour;
        
        widget.reactionData == '' ? startHour = (new Date().getHours() + 2) : startHour = parseInt(widget.reactionData);
        if (isNaN(startHour) || startHour <= (new Date().getHours() + 1) || startHour > 23)
            startHour = (new Date().getHours() + 2);

        startHour = startHour - 1; // corrige le décalage horaire

        const startDate = new Date();
        startDate.setHours(startHour);
        startDate.setMinutes(0);
        
        const endDate = new Date();
        endDate.setHours((startHour + 1));
        endDate.setMinutes(0);
        
        const start = startDate.toISOString();
        const end = endDate.toISOString();
        
        fetch('https://www.googleapis.com/calendar/v3/calendars/primary/events', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + widget.serviceOutToken
            },
            body: JSON.stringify({
                    "end": {
                      "dateTime": end
                    },
                    "start": {
                      "dateTime": start
                    },
                    "summary": "Event from AREA"
                })
        })
        .then(res => res.json())
        .then(result => {
        })
        .catch(function (error) {
            console.log(error);
        });

    },
}