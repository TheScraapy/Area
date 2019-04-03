const AuthPolicy = require('./policies/AuthPolicy');
const AuthController = require('./controllers/AuthController');
const ConfigController = require('./controllers/ConfigController');
const WidgetsController = require('./controllers/WidgetsController');
const passport = require('passport');
const passportConf = require('./passport');

module.exports = (app) => {
    app.post('/auth/register',
        AuthPolicy.register,
        AuthController.register);

    app.post('/auth/login',
        AuthPolicy.register,
        passport.authenticate('local', { session: false, failWithError: true }),
        AuthController.login,
        AuthController.loginFailed);

    app.post('/auth/google',
        passport.authenticate('googleToken', { session: false }),
        AuthController.login);

    app.post('/auth/twitter',
        passport.authenticate('jwt', { session: false }),
        AuthController.twitter);

    app.get('/auth/twitter/callback',
        AuthController.twitterCallback);

    app.post('/auth/:id',
        passport.authenticate('jwt', { session: false }),
        AuthController.oauth);

    app.get('/widgets/services',
        passport.authenticate('jwt', { session: false }),
        WidgetsController.services);

    app.get('/widgets/:id/actions',
        passport.authenticate('jwt', { session: false }),
        WidgetsController.actions);

    app.get('/widgets/:id/reactions',
        passport.authenticate('jwt', { session: false }),
        WidgetsController.reactions);

    app.get('/widgets/all',
        passport.authenticate('jwt', { session: false }),
        WidgetsController.all);

    app.post('/widgets/add',
        passport.authenticate('jwt', { session: false }),
        WidgetsController.add);

    app.post('/widgets/toggle',
        passport.authenticate('jwt', { session: false }),
        WidgetsController.toggle);

    app.post('/widgets/delete',
        passport.authenticate('jwt', { session: false }),
        WidgetsController.delete);

    app.get('/about.json',
        ConfigController.config);
}