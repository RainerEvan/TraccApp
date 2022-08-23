importScripts('https://www.gstatic.com/firebasejs/8.0.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.0.2/firebase-messaging.js');

firebase.initializeApp({
    apiKey: "AIzaSyC1AKF1YxGB46Fn_ibpxRA8R-z_qTkh2Fk",
    authDomain: "tracc-app.firebaseapp.com",
    projectId: "tracc-app",
    storageBucket: "tracc-app.appspot.com",
    messagingSenderId: "552648146786",
    appId: "1:552648146786:web:7f2d4bc722f5a71ba8b63b",
    measurementId: "G-84627433P9"
});

const messaging = firebase.messaging();