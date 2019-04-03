const { Area } = require('../models');
const { Service } = require('../models');
const services = require('../config/about.json');

const Sequelize = require('sequelize');
const Op = Sequelize.Op;

async function getServiceInfo(id, title, email) {
    var logged = await Service.findOne({ where: { email, serviceid: id } }) != null;
    if (title == "Clock")
        logged = true;
    return { id, title, logged };
}

async function getMaxId(email) {
    let firstid = 0;
    const results = await Area.findAll({
        attributes: ['areaid'],
        where: { email },
        raw: true
    });
    results.forEach(result => {
        if (result.areaid != firstid) {
            return (result.areaid);
        }
        firstid++;
    });
    return (firstid);
}

function getFeature(serviceIn, serviceOut, action, reaction) {
    return ("If " + services[serviceIn].actions[action].name.toLowerCase() +
        ", then " + services[serviceOut].reactions[reaction].name.toLowerCase());
}

function getServiceName(serviceId) {
    return (services[serviceId].name);
}

module.exports = {
    async services(req, res) {
        try {
            let response = [];
            for (let i = 0; i < services.length; i++) {
                response.push(await getServiceInfo(i, services[i].name, req.user.email));
            };
            console.log('services', response);
            res.send({ services: response });
        } catch (err) {
            res.status(400).send({
                error: err.message
            });
        }
    },
    async actions(req, res) {
        try {
            let actions = [];
            const id = req.params.id;
            if (services[id].actions) {
                for (let i = 0; i < services[id].actions.length; i++) {
                    actions.push({
                        id: i,
                        title: services[id].actions[i].name,
                        desc: services[id].actions[i].description,
                        isConfigurable: services[id].actions[i].isConfigurable
                    });
                }
            }
            res.send({ actions });
        } catch (err) {
            res.status(400).send({
                error: err.message
            })
        }
    },
    async reactions(req, res) {
        try {
            let reactions = [];
            const id = req.params.id;
            if (services[id].reactions) {
                for (let i = 0; i < services[id].reactions.length; i++) {
                    reactions.push({
                        id: i,
                        title: services[id].reactions[i].name,
                        desc: services[id].reactions[i].description,
                        isConfigurable: services[id].reactions[i].isConfigurable
                    });
                }
            }
            res.send({ reactions });
        } catch (err) {
            res.status(400).send({
                error: err.message
            })
        }
    },
    async all(req, res) {
        try {
            const widgets = await Area.findAll({
                attributes: ['areaid', 'feature', 'serviceIn', 'serviceOut', 'active'],
                where: { email: req.user.email },
                raw: true
            });
            widgets.forEach(widget => {
                widget.serviceIn = getServiceName(widget.serviceIn);
                widget.serviceOut = getServiceName(widget.serviceOut);
                widget.active = Boolean(widget.active);
            });
            res.send({ widgets });
        } catch (err) {
            res.status(400).send({
                error: err.message
            })
        }
    },
    async add(req, res) {
        try {
            const email = req.user.email;
            const { serviceIn, serviceOut, action, reaction, actionData, reactionData } = req.body;
            const areaid = await getMaxId(email);
            const feature = getFeature(serviceIn, serviceOut, action, reaction);
            await Area.create({
                areaid, email, action, reaction, feature, serviceIn, serviceOut, active: 1, timeout: Date.now(),
                lastTime: Date.now(), lastData: null, actionData, reactionData
            });
            res.send({
                areaid,
                feature,
                serviceIn: getServiceName(serviceIn),
                serviceOut: getServiceName(serviceOut),
                active: true
            });
        } catch (err) {
            res.status(400).send({
                error: err.message
            })
        }
    },
    async toggle(req, res) {
        try {
            await Area.update({ active: Number(req.body.state) }, { where: { email: req.user.email, areaid: req.body.areaid } });
            res.send({ status: "toggle ok" });
        } catch (err) {
            res.status(400).send({
                error: err.message
            })
        }
    },
    async delete(req, res) {
        try {
            await Area.destroy({ where: { email: req.user.email, areaid: req.body.areaid } });
            res.send({ status: "delete ok" });
        } catch (err) {
            res.status(400).send({
                error: err.message
            })
        }
    }
}
