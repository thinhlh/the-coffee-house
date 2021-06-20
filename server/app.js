const express = require('express');
const admin = require('firebase-admin');
const helper = require('./helper');
const app = express();

app.listen(process.env.PORT || 3000);

app.get('/', (req, res) => {
    res.send('<h1>The Coffee House Server</h1>');
});

app.get('/users', (req, res) => {
    if (helper.authorize(req)) {
        helper.listAllUsers().then((result) => {
            res.status(result == null ? 404 : 200).send(result == null ? 'Somthing happened' : result);
        });
    } else {
        helper.denied(res);
    }
});

app.get('/user-info/:id', (req, res) => {
    if (helper.authorize(req)) {
        helper.getUserInfo(req.params.id).then((result) => {
            res.status(result == null ? 404 : 200).send(result == null ? 'Somthing happended' : result);
        });
    }
    else {
        helper.denied(res);
    }
});


app.delete('/delete-user/:id', (req, res) => {
console.log('called delete');
    if (helper.authorize(req)) {
        const userId = req.params.id;

        helper.deleteUser(userId).then(() => {
            console.log('Successfully delete user');
            res.status(200).send('Success delete user');
        }).catch((error) => {
            console.log('Internal server failure', error);
            res.status(502).send(error)
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