module.exports = (sequelize, DataTypes) => {

    const Area = sequelize.define('Area', {
        areaid: DataTypes.INTEGER,
        email: DataTypes.STRING,
        action: DataTypes.INTEGER,
        reaction: DataTypes.INTEGER,
        feature: DataTypes.STRING,
        serviceIn: DataTypes.INTEGER,
        serviceOut: DataTypes.INTEGER,
        active: DataTypes.INTEGER,
        timeout: DataTypes.INTEGER,
        lastTime: DataTypes.INTEGER,
        lastData: DataTypes.STRING,
        actionData: DataTypes.STRING,
        reactionData: DataTypes.STRING
    });

    return Area;
}