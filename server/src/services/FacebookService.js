const { FB, FacebookApiException } = require('fb');
const SequelizeController = require('../controllers/SequelizeController');

module.exports = {
	/* Actions */
	async newFriend(widget, callback) {
		FB.setAccessToken(widget.serviceInToken);
		console.log('Checking for a new friend ...');
		FB.api('/me/friends', 'GET', {}, function (response) {
			if (!response || response.error) {
				console.log(!response ? 'error occurred' : response.error);
			} else {
				if (widget.lastData != null && widget.lastData < response.summary.total_count) {
					callback(widget, 'You now have ' + response.summary.total_count + ' friends !');
				}
				SequelizeController.updateService(widget.id, response.summary.total_count);
			}
		});
	},
	async newProfilePicture(widget, callback) {
		FB.setAccessToken(widget.serviceInToken);
		console.log('Checking for a new profile picture ...');
		FB.api('/me/picture?redirect=false', 'GET', {}, function (res) {
			if (!res || res.error) {
				console.log(!res ? 'error occurred' : res.error);
			} else {
				if (widget.lastData != null && res.data.url != widget.lastData) {
					callback(widget, 'You changed your profile picture');
				}
				SequelizeController.updateService(widget.id, res.data.url);
			}
		})
	}
}
	