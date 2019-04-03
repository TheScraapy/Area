module.exports = (sequelize, DataTypes) => {
    const Service = sequelize.define('Service', {
        email: DataTypes.STRING,
        serviceid: DataTypes.INTEGER,
        token: DataTypes.STRING
    });

    return Service;
}