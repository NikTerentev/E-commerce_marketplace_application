// Import the functions you need from the SDKs you need
import {
    initializeApp
} from "https://www.gstatic.com/firebasejs/10.8.1/firebase-app.js";
import {
    getMessaging, getToken, onMessage, deleteToken
} from "https://www.gstatic.com/firebasejs/10.8.1/firebase-messaging.js";

const firebaseConfig = {

    apiKey: "AIzaSyClKdEFYwWvjfU3c_RicOABeZiF-Xuipgc",

    authDomain: "e-commerce-4a7dd.firebaseapp.com",

    projectId: "e-commerce-4a7dd",

    storageBucket: "e-commerce-4a7dd.appspot.com",

    messagingSenderId: "26989995957",

    appId: "1:26989995957:web:c3fe26a5459ed128461bfc"

};

// Initialize Firebase
initializeApp(firebaseConfig);
const messaging = getMessaging();
let vapidKey = "BHrkXYN7WAl_FBwjjezvb_w4-4w4QtSCQWXdAY8WwZ93pqmZARiUawTxFj4wZ9m_0lOB2kg1cLAV5DK4LAXOjso";

function resetUI() {
    getToken(messaging, {vapidKey}).then((currentToken) => {
        if (currentToken) {
            sendTokenToServer(currentToken);
        } else {
            console.log('No registration token available. Request permission to generate one.');
            setTokenSentToServer(false);
        }
    }).catch((err) => {
        console.log('An error occurred while retrieving token. ', err);
        setTokenSentToServer(false);
    });
}

function sendTokenToServer(currentToken) {
    if (!isTokenSentToServer()) {
        console.log('Sending token to server...', currentToken);

        if (stompClient) {
            const tokenMessage = {
                nickName: nickname,
                token: currentToken,
                action: "add",
            };
            stompClient.send('/app/token', {}, JSON.stringify(tokenMessage));
        }

        setTokenSentToServer(true);
    } else {
        console.log("Token already sent to server so won\\'t send it again unless it changes")
    }
}

function isTokenSentToServer() {
    return window.localStorage.getItem('sentToServer') === '1';
}

function setTokenSentToServer(sent) {
    window.localStorage.setItem('sentToServer', sent ? '1' : '0');
}


function deleteTokenFromFirebase() {
    getToken(messaging, {vapidKey}).then((currentToken) => {
        console.log('Token deleted.', currentToken);
        setTokenSentToServer(false);
        if (stompClient) {
            const tokenMessage = {
                nickName: nickname,
                token: currentToken,
                action: "delete",
            };
            stompClient.send('/app/token', {}, JSON.stringify(tokenMessage));
        }
    })
}

function requestPermission() {
    console.log('Requesting permission...');
    Notification.requestPermission().then((permission) => {
        if (permission === 'granted') {
            console.log('Notification permission granted.');
            resetUI();
        } else {
            console.log('Unable to get permission to notify.');
        }
    });
}

if (isTokenSentToServer()) {
    document.querySelector("#customSwitch").setAttribute("checked", "true");
}

document.querySelector('#customSwitch').addEventListener('change', function (event) {
    if (event.target.checked) {
        requestPermission();
    } else {
        deleteTokenFromFirebase()
    }
});

'use strict';
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const connectingElement = document.querySelector('.connecting');
const chatArea = document.querySelector("#chat-messages");


let stompClient = null;
let nickname = null;
let selectedUserId = null;


function connect() {
    nickname = document.querySelector('#nickname').value.trim();

    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    stompClient.subscribe(`/user/${nickname}/queue/messages`, onMessageReceived);
    stompClient.subscribe('/user/public', onMessageReceived);

    // Подключение пользователя
    stompClient.send('/app/user.connectUser',
        {},
        JSON.stringify({nickName: nickname})
    );
    document.querySelector('#connected-user-fullname').textContent = nickname;
    // Найти и отобразить подключённых пользователей
    findAndDisplayConnectedUsers().then();
}


async function findAndDisplayConnectedUsers() {
    const connectedUserResponse = await fetch('/users')
    let connectedUsers = await connectedUserResponse.json();
    connectedUsers = connectedUsers.filter(user => user.nickname !== nickname);
    const connectedUsersList = document.getElementById('connectedUsers');
    connectedUsersList.innerHTML = '';

    connectedUsers.forEach(user => {
        appendUserElement(user, connectedUsersList);
        if (connectedUsers.indexOf(user) < connectedUsers.length - 1) {
            // Добавляем разделитель
            const separator = document.createElement('li');
            separator.classList.add('separator');
            connectedUsersList.appendChild(separator);
        }
    });
}

function appendUserElement(user, connectedUsersList) {
    const listItem = document.createElement('li');
    listItem.classList.add('user-item');
    listItem.id = user.nickname;

    const userImage = document.createElement('img');
    userImage.src = '../img/user_icon.png';
    userImage.alt = user.name;

    const usernameSpan = document.createElement('span');
    usernameSpan.textContent = user.name;

    const receivedMsgs = document.createElement('span');
    receivedMsgs.textContent = '0';
    receivedMsgs.classList.add('nbr-msg', 'd-none');

    listItem.appendChild(userImage);
    listItem.appendChild(usernameSpan);
    listItem.appendChild(receivedMsgs);

    listItem.addEventListener('click', userItemClick);

    connectedUsersList.appendChild(listItem);
}

function userItemClick(event) {
    document.querySelectorAll('.user-item').forEach(item => {
        item.classList.remove('active');
    });
    messageForm.classList.remove('d-none');

    const clickedUser = event.currentTarget;
    clickedUser.classList.add('active');

    selectedUserId = clickedUser.getAttribute('id');
    fetchAndDisplayUserChat().then();

    const nbrMsg = clickedUser.querySelector('.nbr-msg');
    nbrMsg.classList.add('d-none');
}

async function fetchAndDisplayUserChat() {
    const userChatResponse = await fetch(`/messages/${nickname}/${selectedUserId}`);
    const userChat = await userChatResponse.json();
    chatArea.innerHTML = '';
    userChat.forEach(chat => {
        displayMessage(chat.senderId, chat.content, chat.timestamp);
    });
    chatArea.scrollTop = chatArea.scrollHeight;
}

function displayMessage(senderId, content, timestamp) {
    const messageContainer = document.createElement('div');
    messageContainer.classList.add('message');
    if (senderId === nickname) {
        messageContainer.classList.add('sender');
    } else {
        messageContainer.classList.add('receiver');
    }
    const message = document.createElement('p');
    message.textContent = content;
    messageContainer.appendChild(message);

    const dateObject = new Date(timestamp);
    let day = dateObject.getDate(); // день
    let month = dateObject.getMonth() + 1; // месяцы начинаются с 0
    const year = dateObject.getFullYear(); // год
    let hours = dateObject.getHours(); // часы
    let minutes = dateObject.getMinutes(); // минуты

// Добавляем ведущие нули, если значения меньше 10
    day = day < 10 ? `0${day}` : day;
    month = month < 10 ? `0${month}` : month;
    hours = hours < 10 ? `0${hours}` : hours;
    minutes = minutes < 10 ? `0${minutes}` : minutes;

    // Форматируем дату и время
    const formattedDate = `${hours}:${minutes} ${day}.${month}.${year} `;

    const date = document.createElement('p');
    date.textContent = formattedDate;
    messageContainer.appendChild(date);
    chatArea.appendChild(messageContainer);
}

function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    const timestamp = new Date();
    if (messageContent && stompClient) {
        const chatMessage = {
            senderId: nickname,
            recipientId: selectedUserId,
            content: messageContent,
            timestamp: timestamp
        };
        stompClient.send('/app/chat', {}, JSON.stringify(chatMessage));
        displayMessage(nickname, messageContent, timestamp);
    }
    chatArea.scrollTop = chatArea.scrollHeight;
    event.preventDefault();
}

async function onMessageReceived(payload) {
    await findAndDisplayConnectedUsers();
    const message = JSON.parse(payload.body);
    if (selectedUserId && selectedUserId === message.senderId) {
        displayMessage(message.senderId, message.content);
        chatArea.scrollTop = chatArea.scrollHeight;
    }

    if (selectedUserId) {
        document.querySelector(`#${selectedUserId}`).classList.add('active');
    } else {
        messageForm.classList.add('d-none');
    }

    const notifiedUser = document.querySelector(`#${message.senderId}`);
    if (notifiedUser && !notifiedUser.classList.contains('active')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('d-none');
        nbrMsg.textContent = '';
    }
}

connect();
messageForm.addEventListener('submit', sendMessage, true);
