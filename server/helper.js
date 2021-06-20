const AUTHORIZATION_KEY = 'the-coffee-house';

const admin = require('firebase-admin');

var serviceAccount = require("./the-coffee-house-212b6-firebase-adminsdk-h4fw8-08bdf6678e.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://the-coffee-house-212b6-default-rtdb.firebaseio.com"
});

const db= admin.firestore();

const listAllUsers = async (nextPageToken) => {
    var result = await admin
        .auth()
        .listUsers(1000, nextPageToken);
    return result.users;
};

const getUserInfo = async (uid) => {
    return await admin.auth().getUser(uid);
}

const deleteUser= async (uid)=>{
    console.log("called");
    await admin.auth().deleteUser(uid);
    await db.collection("users").doc(uid).delete();
}

const authorize = (req) => {
    return req.headers.authorization === AUTHORIZATION_KEY;
}

const denied = (res) => {
    res.status(403).send('Permission Denied');
}

module.exports = { listAllUsers, authorize, getUserInfo, deleteUser, denied, };