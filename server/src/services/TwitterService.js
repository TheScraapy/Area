const fetch = require('node-fetch');
const Twitter = require('twitter');
const SequelizeController = require('../controllers/SequelizeController');

function parseToken(token){
	var splitted = token.split(' ')
	
	if (splitted == null || splitted[0] == null || splitted[1] == null) {
		console.log("Error parsing twitter token");
		return null;	
	}

	var T = new Twitter({
		consumer_key: '1SlXbsRd5HKgdA6pzaG2jfuQb',
		consumer_secret: 'PtgmZ21I56XMkIrGtdZQBEeF89QD97NAlXXqQIWGJKFANnsT3t',
		access_token_key: splitted[0],
		access_token_secret: splitted[1]
	})

	return T;
}

module.exports = {
    /* Actions */
    async hashtag(widget, callback) {
		
		T = parseToken(widget.serviceInToken);

		const params = {
			q: '#area',
			count: 1,
			result_type: 'recent',
			lang: 'en'
		}

		T.get('search/tweets', params, (err, data, response) => {
			if(err){
			  console.log("err is here: ", err);
			  return;
			}
			
			const tweetsId = data.statuses
			  .map(tweet => ({ id: tweet.id_str }));
			  if (widget.lastData != tweetsId && widget.lastData != null)
					callback(widget, 'Somebody tweeted in your hashtag !');	
				SequelizeController.updateService(widget.id, tweetsId);
		  })
	},
	async tweet(widget, callback) {
	
	T = parseToken(widget.serviceInToken);
	
	const params = {
		count: 1,
		result_type: 'recent',
		lang: 'en'
	}

	T.get('statuses/user_timeline', params, (err, data, response) => {
		if(err){
		  console.log("err is here: ", err);
		  return;
		}
		if (widget.lastData != data[0].id && widget.lastData != null) {
			callback(widget, "You tweeted: " + data[0].text);
		}
		SequelizeController.updateService(widget.id, data[0].id);
	  })
    },
    /* Reactions */
    async tweets(widget, data) {
		T = parseToken(widget.serviceOutToken);
		T.post('statuses/update', { status: data }, function(err, data, response) {
			console.log(err)
			console.log("Somebody tweeted");
		});
	},
}   