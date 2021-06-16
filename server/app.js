const express = require('express');
const admin = require('firebase-admin');
const helper = require('./helper');
const app = express();

var serviceAccount = require("./the-coffee-house-212b6-firebase-adminsdk-h4fw8-08bdf6678e.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://the-coffee-house-212b6-default-rtdb.firebaseio.com"
});

app.listen(process.env.PORT || 3000);

app.get('/', (req, res) => {
    res.send('<h1>The Coffee House Server</h1>');
});

// app.get('/users_depricate', (req, res) => {
//     res.redirect('/users');
// });

app.get('/users', (req, res) => {
    if (helper.authorize(req)) {
        helper.listAllUsers().then((result) => {
            res.status(result == null ? 404 : 200).send(result == null ? 'Somthing happened' : result);
        });
    } else {
        helper.denied(res);
    }
});


app.delete('/delete_user/:id', (req, res) => {

    if (helper.authorize(req)) {
        const userId = req.params.id;

        admin.auth().deleteUser(userId)
            .then(() => {
                console.log('Successfully delete user');
                res.status(200).send('Success delete user');
            })
            .catch((error) => {
                console.log('Error while deleting user', error);
                res.status(405).send(error);
            });
    } else {
        helper.denied(res);
    }
});

app.get('/test', (req, res) => {
    const auth = req.headers.authorization;
    if (helper.authorize(req)) {
        res.send('Authorized');
    }
    else {
        helper.denied(res);
    }
});