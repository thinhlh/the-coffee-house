const express = require('express');
const admin = require('firebase-admin');
const helper = require('./helper');
const app = express();

app.listen(process.env.PORT || 3000);
app.use(express.json());

setInterval(() => helper.updateMembership(), 1000 * 3600 * 24 * 5);

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
            console.log(result);
            res.status(result == null ? 404 : 200).send(result == null ? 'Somthing happended' : result);
        });
    }
    else {
        helper.denied(res);
    }
});

app.get('/profit/', (req, res) => {
    if (helper.authorize(req)) {
        if(req.query.fromDate==undefined||req.query.toDate==undefined){
            res.send(400);
        }
        else{
            var result = helper.getProfit(req.query.fromDate, req.query.toDate);
            result.then((total) => {
                res.status(200).send(total);
            });
        }
    }
    else {
        helper.denied(res);
    }
});


app.delete('/delete-user/:id', (req, res) => {
    if (helper.authorize(req)) {
        const userId = req.params.id;

        helper.deleteUser(userId).then(() => {
            console.log('Successfully delete user');
            res.status(200).send('Success delete user');
        }).catch((error) =>
            res.sendStatus(502)
        );
    } else {
        helper.denied(res);
    }
});

app.post('/push-notification', (req, res) => {
    if (helper.authorize(req)) {
        helper.pushNotification(req.body).then(
            (value) => {
                console.log('Success', value);
                res.status(200).send(value);
            }
        ).catch((error) => {
            console.log(error);
            res.sendStatus(500);
        });
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