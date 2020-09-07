importScripts("https://www.gstatic.com/firebasejs/7.16.1/firebase-app.js");
importScripts(
    "https://www.gstatic.com/firebasejs/7.16.1/firebase-messaging.js",
);
// For an optimal experience using Cloud Messaging, also add the Firebase SDK for Analytics.
importScripts(
    "https://www.gstatic.com/firebasejs/7.16.1/firebase-analytics.js",
);

// Initialize the Firebase app in the service worker by passing in the
// messagingSenderId.
firebase.initializeApp({
    messagingSenderId: "856140000238",
    apiKey: "AIzaSyAQ2PMk54tvLDjydmdalHZVqpYh88T7aek",
    projectId: "thuctapdevmaster01",
    appId: "1:856140000238:web:8e7fa3c8f4ac9b30aebcff",
});
var linkEven = ""
// Retrieve an instance of Firebase Messaging so that it can handle background
// messages.
const messaging = firebase.messaging();
// self.addEventListener('notificationclick', function(event){
//     event.notification.close();
//     event.waitUntil(
//             clients.openWindow("https://demo-thongbao.herokuapp.com"));
// });
self.addEventListener('notificationclick', function(event){
    event.notification.close();
    event.waitUntil(
            clients.openWindow(linkEven));
});
messaging.setBackgroundMessageHandler(function(payload) {
    linkEven = payload.data.link;
    console.log(
        "[firebase-messaging-sw.js] Received background message ",
        payload,
    );
    // Customize notification here
    const notificationTitle = payload.data.title;
    const notificationOptions = {
        body: payload.data.content,
        icon: "/itwonders-web-logo.png",
    };
    return self.registration.showNotification(
        notificationTitle,
        notificationOptions,
    );
});