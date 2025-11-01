***

# 💬 Real-Time Chatroom (React + Spring Boot WebSocket)

A simple **Discord-style real-time chat application** built using **React (frontend)** and **Spring Boot WebSocket (backend)** — with live messaging and a clean dark UI.

***

## 🚀 Features

- 🔗 Real-time public chat powered by Spring’s WebSocket  
- 👥 Join chat with any username  
- 💻 Discord-inspired dark interface  
- ⚡ Instantly broadcast messages to all connected users  
- 🧩 Built with:
  - React + Vite (Frontend)
  - Spring Boot (Backend using `TextWebSocketHandler`)

***

## 🏗️ Project Structure

```
project-root/
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/
│   │                   └── chat/
│   │                       ├── BackendApplication.java
│   │                       ├── config/
│   │                       │   └── WebSocketConfig.java
│   │                       └── server/
│   │                           └── ChatHandler.java
│   ├── pom.xml
│   └── ...
│
└── frontend/
    ├── src/
    │   ├── App.js
    │   ├── App.css
    │   ├── index.js
    │   └── components/
    │       ├── ChatRoom.js
    │       └── JoinScreen.js
    ├── index.html
    ├── package.json
    └── vite.config.js
```

***

## ⚙️ Backend Setup (Spring Boot)

### Prerequisites  
- Java 17+  
- Maven  

### Run the application  
```bash
cd backend
mvn spring-boot:run
```
Starts your Spring Boot server at:  
http://localhost:8080  

WebSocket endpoint:  
ws://localhost:8080/chat

***

## 💻 Frontend Setup (React + Vite)

### Navigate to frontend folder  
```bash
cd frontend
```

### Install dependencies  
```bash
npm install
```

### Run the development server  
```bash
npm run dev
```

Visit:  
http://localhost:5173

***

## 🔌 WebSocket Connection

In your `ChatRoom.js`, ensure the WebSocket connection URL matches your backend:  
```js
const socket = new WebSocket("ws://localhost:8080/chat");
```
Change this URL if you deploy the backend elsewhere (Render, AWS, Railway, etc).

***

## 🧠 How It Works

- Enter username on Join screen  
- React app connects to backend via WebSocket handler  
- Messages are sent to backend and broadcast to all clients  
- All users see real-time updates instantly  

***

## 🎨 UI Overview

- **Join Screen:** Enter username  
- **Chat Screen:** Sidebar (active users) + Chat area + Message input  
- **Theme:** Discord-inspired dark layout  

***

## 🧰 Tech Stack

| Area      | Technology         |
|-----------|--------------------|
| Frontend  | React + Vite       |
| Styling   | Custom CSS         |
| Realtime  | Spring WebSocket   |
| Backend   | Spring Boot        |
| Language  | Java + JavaScript  |

***

## 🧪 Future Enhancements

- ✅ Show active users in sidebar  
- ✅ Display timestamps  
- 🕓 Message persistence (MongoDB or PostgreSQL)  
- 💬 Private or group chat rooms  
- 🔐 Authentication (JWT or OAuth)  

***

## 🧑‍💻 Author

**Hardhik Bangera**   
📧 [hardhikbangera@gmail.com](mailto:hardhikbangera@gmail.com)  
💼 [https://github.com/hardhikbangera](https://github.com/hardhikbangera)

***
