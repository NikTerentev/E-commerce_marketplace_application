importScripts("https://www.gstatic.com/firebasejs/10.8.1/firebase-app-compat.js");
importScripts("https://www.gstatic.com/firebasejs/10.8.1/firebase-messaging-compat.js");

const firebaseConfig = {

    apiKey: "AIzaSyClKdEFYwWvjfU3c_RicOABeZiF-Xuipgc",

    authDomain: "e-commerce-4a7dd.firebaseapp.com",

    projectId: "e-commerce-4a7dd",

    storageBucket: "e-commerce-4a7dd.appspot.com",

    messagingSenderId: "26989995957",

    appId: "1:26989995957:web:c3fe26a5459ed128461bfc"

};

// Initialize Firebase
firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();
