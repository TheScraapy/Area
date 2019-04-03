const passport = require('passport');
const JwtStrategy = require('passport-jwt').Strategy;
const { ExtractJwt } = require('passport-jwt');
const LocalStrategy = require('passport-local').Strategy;
const GooglePlusStrategy = require('passport-google-plus-token');
const config = require('./config/config');
const { User } = require('./models');
const { Service } = require('./models');

// Json Web Tokens Strategy
passport.use(new JwtStrategy({
    jwtFromRequest: ExtractJwt.fromHeader('authorization'),
    secretOrKey: config.authentication.jwtSecret
}, async (payload, done) => {
    try {
        const user = await User.findOne({
            where: {
                email: payload.sub
            }
        });
        if (!user) {
            return done(null, false);
        }
        done(null, user);
    } catch (error) {
        done(error, false);
    }
}));

// Google OAuth Strategy
passport.use('googleToken', new GooglePlusStrategy({
    clientID: config.authentication.googleClientID,
    clientSecret: config.authentication.googleClientSecret
}, async (accessToken, refreshToken, profile, done) => {
    try {
        const existingUser = await User.findOne({
            where: {
                $or: [
                    { googleid: profile.id },
                    { email: profile.emails[0].value }
                ]
            }
        });
        if (existingUser) {
            return done(null, existingUser);
        }
        const newUser = await User.create({
            googleid: profile.id,
            email: profile.emails[0].value
        });
        done(null, newUser);
    } catch (error) {
        done(error, false, error.message);
    }
}));

// Local Strategy
passport.use(new LocalStrategy({
    usernameField: 'email',
    passReqToCallback: true
}, async (req, email, password, done) => {
    try {
        const user = await User.findOne({
            where: {
                email: email,
                googleid: null
            }
        });
        if (!user) {
            req.error = 'Unknown account';
            return done(null, false);
        }
        const isPasswordValid = await user.comparePassword(password);
        if (!isPasswordValid) {
            req.error = 'Password invalid';
            return done(null, false);
        }
        done(null, user);
    } catch (error) {
        done(error, false);
    }
}));