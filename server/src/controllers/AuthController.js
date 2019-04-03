const { User } = require('../models');
const { Service } = require('../models');
const jwt = require('jsonwebtoken');
const config = require('../config/config');
const Twitter = require('twitter-lite')


function jwtSignUser(user) {
    return jwt.sign({
        iss: 'TeamPoney',
        sub: user.email,
        iat: new Date().getTime(),
        exp: new Date().setDate(new Date().getDate() + 7)
    }, config.authentication.jwtSecret);
}

const client = new Twitter({
    consumer_key: '1SlXbsRd5HKgdA6pzaG2jfuQb',
    consumer_secret: 'PtgmZ21I56XMkIrGtdZQBEeF89QD97NAlXXqQIWGJKFANnsT3t'
});

async function getServiceToken(email, serviceid) {
    const service = await Service.findOne({
        attributes: ['token'],
        where: { email, serviceid },
        raw: true
    });
    return (service ? service.token : null);
}

module.exports = {
    async register(req, res) {
        try {
            const user = await User.create(req.body);
            const token = jwtSignUser(user.toJSON());
            res.send({ token });
        } catch (err) {
            res.status(400).send({
                error: 'This email account is already in use.'
            });
        }
    },
    async login(req, res) {
        try {
            const token = jwtSignUser(req.user);
            res.send({ token });
        } catch (err) {
            res.status(400).send({ error: err.message });
        }
    },
    async loginFailed(err, req, res, next) {
        try {
            if (req.error) {
                res.status(400).send({ error: req.error });
            }
        } catch (err) {
            res.status(400).send({ error: err.message });
        }
    },
    async oauth(req, res) {
        try {
            await Service.create({
                email: req.user.email,
                serviceid: req.params.id,
                token: req.body.access_token
            })
            res.status(200).send({ status: "valid token" });
        } catch (err) {
            res.status(400).send({ error: err.message });
        }
    },
    async twitter(req, res) {
        const { oauth_token, oauth_token_secret } = await client.getRequestToken('http://localhost:8080/auth/twitter/callback?email=' + req.user.email)
            .then(res => res)
            .catch();

        try {
            await Service.create({
                email: req.user.email,
                serviceid: 2,
                token: oauth_token + ' ' + oauth_token_secret
            })
            res.status(200).send({ status: "request_tokens ok", oauth_token: oauth_token, oauth_token_secret: oauth_token_secret });
        } catch (err) {
            res.status(400).send({ error: err.message });
        }
    },
    async twitterCallback(req, res) {
        var token = await getServiceToken(req.query.email, 2);
        var oauth_tokens = token.split(' ');
        const options = {
            key: req.query.oauth_token,
            secret: oauth_tokens[1],
            verifier: req.query.oauth_verifier
        };

        const { oauth_token, oauth_token_secret } = await client.getAccessToken(options)
            .then(res => res)
            .catch();

        var email = req.query.email;
        var serviceid = 2;

        try {
            await Service.update({
                token: oauth_token + ' ' + oauth_token_secret
            }, {
                    where: { email, serviceid }
                });
            res.status(200).send({ status: "Login successful, you can close this window ( ͡° ͜ʖ ͡°)" });
        } catch (err) {
            res.status(400).send({ error: err.message });
        }
    }
}