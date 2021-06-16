const AUTHORIZATION_KEY='the-coffee-house';

const admin = require('firebase-admin');

const listAllUsers = async (nextPageToken) => {
    var result = await admin
        .auth()
        .listUsers(1000, nextPageToken);
    return result.users;
};

const authorize = (req) => {
    return req.headers.authorization === AUTHORIZATION_KEY;
}

const denied = (res) => {
    res.status(403).send('Permission Denied');
}

module.exports = { listAllUsers, authorize, denied, };