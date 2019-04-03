const fetch = require('node-fetch');
const SequelizeController = require('../controllers/SequelizeController');

module.exports = {
    /* Actions */
    async newSubscribtion(widget, callback) {
        console.log('Checking for a new subscription ...');
        fetch('https://www.googleapis.com/youtube/v3/subscriptions/?part=snippet%2CcontentDetails&mine=true&maxResults=1&order=relevance', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + widget.serviceInToken
            }
        })
            .then(res => res.json())
            .then(result => {
                const currNb = result.pageInfo.totalResults;
                const currId = result.items[0].id;
                if (widget.lastData != null) {
                    const lastNb = widget.lastData.split(' ')[0];
                    const lastId = widget.lastData.split(' ')[1];
                    if (currNb > lastNb && currId != lastId) {
                        callback(widget, 'I just subscribed to ' + result.items[0].snippet.title + ' on Youtube !');
                    }
                }
                SequelizeController.updateService(widget.id, currNb + ' ' + currId);
            })
            .catch(function (error) {
                console.log(error);
            });
    },
    async newSubscribers(widget, callback) {
        console.log('Checking for new subscribers ...');
        fetch('https://www.googleapis.com/youtube/v3/channels/?part=statistics&mine=true', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + widget.serviceInToken
            }
        })
            .then(res => res.json())
            .then(result => {
                const subscribersCount = result.items[0].statistics.subscriberCount;
                if (subscribersCount > widget.lastData) {
                    callback(widget, subscribersCount - widget.lastData + ' people subcribed to my Youtube channel !');
                }
                SequelizeController.updateService(widget.id, subscribersCount);
            })
            .catch(function (error) {
                console.log(error);
            });
    },
    async newLikedVideo(widget, callback) {
        console.log('Checking for a new liked ...');
        fetch('https://www.googleapis.com/youtube/v3/videos/?part=snippet&myRating=like', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + widget.serviceInToken
            }
        })
            .then(res => res.json())
            .then(result => {
                const currNb = result.pageInfo.totalResults;
                const currVideo = result.items[0].id;
                if (widget.lastData != null) {
                    const lastNb = widget.lastData.split(' ')[0];
                    const lastVideo = widget.lastData.split(' ')[1];
                    if (currNb > lastNb && currVideo != lastVideo) {
                        callback(widget, 'I just liked this video https://www.youtube.com/watch?v=' + currVideo + ' on Youtube !');
                    }
                }
                SequelizeController.updateService(widget.id, currNb + ' ' + currVideo);
            })
            .catch(function (error) {
                console.log(error);
            });
    }
    /* Reactions */
}