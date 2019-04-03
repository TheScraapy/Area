module.exports = {
    port: process.env.PORT || 8080,
    db: {
        database: process.env.DB_NAME || 'area',
        user: process.env.DB_USER || 'area',
        password: process.env.DB_PASS || 'area',
        options: {
            dialect: process.env.DIALECT || 'sqlite',
            host: process.env.HOST || 'localhost',
            storage: './area.sqlite'
        }
    },
    authentication: {
        jwtSecret: process.env.JWT_SECRET || 'H9nbDHXE4Lpdd6rkjARD',
        googleClientID: '348333225838-43rcuddes9uftacun2drni5h3hpkbja9.apps.googleusercontent.com',
        googleClientSecret: 'z5db-TwxReLvApBtIpDQlwHZ'
    }
}
