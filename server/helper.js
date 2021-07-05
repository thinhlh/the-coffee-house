const AUTHORIZATION_KEY = 'the-coffee-house';

const admin = require('firebase-admin');

var serviceAccount = require("./the-coffee-house-212b6-firebase-adminsdk-h4fw8-08bdf6678e.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://the-coffee-house-212b6-default-rtdb.firebaseio.com"
});

const db = admin.firestore();

const listAllUsers = async (nextPageToken) => {
    var result = await admin
        .auth()
        .listUsers(1000, nextPageToken);
    return result.users;
};

const getUserInfo = async (uid) => {
    var normalInfo= await admin.auth().getUser(uid);
    var userOrders=await admin.firestore().collection('orders').where('userId','==',uid).get();
    var res=normalInfo.toJSON();
    res['totalOrders']=userOrders.docs.length;
    return res;
}

const deleteUser = async (uid) => {
    await admin.auth().deleteUser(uid);
    await db.collection("users").doc(uid).delete();
}

const pushNotification = async (notification) => {
    const message = {
        notification: {
            title: notification.title,
            body: notification.body,
        }
    };
    return admin.messaging().sendToTopic("notifications", message);
}

const authorize = (req) => {
    return req.headers.authorization === AUTHORIZATION_KEY;
}

const denied = (res) => {
    res.status(403).send('Permission Denied');
}

const getProfit = async (fromDate, toDate) => {
    let startDate = new Date(fromDate);

    //Because this is start of the date => we have to make it to the next day in order to cover the last day value
    let endDate = new Date(toDate);
    endDate.setDate(endDate.getDate() + 1);

    var total = 0, delivered = 0;
    var orders = await db.collection('orders').where('orderTime', '>=', startDate).where('orderTime', '<=', endDate).get();

    for (var order of orders.docs) {
        if (order.data().delivered == true)
            delivered++;
        total+=order.data().orderValue;
    }
    return { total: total, numberOfOrders: orders.size, deliveredOrders: delivered };
}

const updateMembership = async () => {
    db.collection('users').get().then(async (documentSnapshots) => {
        await documentSnapshots.forEach(documentRef => {
            var point = documentRef.data().point;
            var membership = documentRef.data().membership;

            if (point >= 0 && point < 500 && membership != 'Bronze') {
                documentRef.ref.update({ membership: 'Bronze' });
            }
            else if (point >= 500 && point < 1000 && membership != 'Silver') {
                documentRef.ref.update({ membership: 'Silver' });
            }
            else if (point >= 1000 && point < 1500 && membership != 'Gold') {
                documentRef.ref.update({ membership: 'Gold' });
            }
            else if (point >= 1500 && membership != 'Diamond') {
                documentRef.ref.update({ membership: 'Diamond' });
            }
        });
    });
    console.log('Updated');
}

module.exports = {
    listAllUsers,
    authorize,
    getUserInfo,
    deleteUser,
    pushNotification,
    updateMembership,
    getProfit,
    denied,
};