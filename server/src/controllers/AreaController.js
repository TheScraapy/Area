const { Area } = require('../models');
const { Service } = require('../models');

const Sequelize = require('sequelize');
const Op = Sequelize.Op;

const YoutubeService = require('../services/YoutubeService');
const FacebookService = require('../services/FacebookService');
const TwitterService = require('../services/TwitterService');
const ClockService = require('../services/ClockService');
const GmailService = require('../services/GmailService');
const CalendarService = require('../services/CalendarService');

const actions = [
    [YoutubeService.newSubscribtion, YoutubeService.newSubscribers, YoutubeService.newLikedVideo],
    [FacebookService.newFriend, FacebookService.newProfilePicture],
    [TwitterService.hashtag, TwitterService.tweet],
    [ClockService.everyDayAt, ClockService.everyHourAt],
    [],
    [CalendarService.detectEvent]
];

const reactions = [
    [],
    [],
    [TwitterService.tweets],
    [],
    [GmailService.sendMail],
    [CalendarService.createEvent]
]

async function getServiceToken(email, serviceid) {
    const service = await Service.findOne({
        attributes: ['token'],
        where: { email, serviceid },
        raw: true
    });
    return (service ? service.token : null);
}

module.exports = {
    async areaRoutine() {
        console.log('\nStarting routine ...');
        const widgets = await Area.findAll({
            attributes: ['id', 'email', 'action', 'reaction', 'serviceIn', 'serviceOut', 'lastTime', 'lastData', 'actionData', 'reactionData'],
            where: { active: 1, timeout: { [Op.lt]: Date.now() } },
            raw: true
        });
        try {
            for (let i = 0; i < widgets.length; i++) {
                widgets[i].serviceInToken = await getServiceToken(widgets[i].email, widgets[i].serviceIn);
                widgets[i].serviceOutToken = await getServiceToken(widgets[i].email, widgets[i].serviceOut);
                actions[widgets[i].serviceIn][widgets[i].action](widgets[i], reactions[widgets[i].serviceOut][widgets[i].reaction]);
            }
        } catch (error) {
            console.log(error);
        }
        setTimeout(() => { this.areaRoutine() }, 10000);
    }
}