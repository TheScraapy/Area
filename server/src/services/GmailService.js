const fetch = require('node-fetch');

module.exports = {
    /* Actions */

    /* Reactions */
    async sendMail(widget, data) {

        var mail = 'To: ' + widget.reactionData + '\nSubject: Area notification\n\n' + data + '\n';
        var encodedmail = Buffer.from(mail).toString('base64')

        fetch('https://www.googleapis.com/gmail/v1/users/me/messages/send', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + widget.serviceOutToken
            },
            body: JSON.stringify({
                'raw': encodedmail
            })
        }).then(result => {
        }).catch(function (error) {
            console.log(error);
        });

    }
}