```md
# 💬 Java Spring WebSocket Chatroom

A simple **real-time chatroom** using **Spring WebSocket (TextWebSocketHandler)** as the backend and a **Java WebSocket client** built with `@ClientEndpoint`.

This project demonstrates how multiple clients can connect to a Spring WebSocket server and exchange real-time messages.

---

## 🧱 Project Structure

```

📂 WebSocketApp/
┣ 📁 backend/              # Spring WebSocket server
┣ 📁 clientApp/            # Java WebSocket client endpoint
┣ 📄 .gitignore
┗ 📄 README.md

````

---

## 🚀 Features

- 📡 Real-time two-way messaging  
- 👥 Multiple clients can connect simultaneously  
- 🔒 Thread-safe session management using `ConcurrentHashMap` and `Set<WebSocketSession>`  
- 🧵 Uses `TextWebSocketHandler` on server side for message handling  
- 💬 Each client sees messages from others but **not their own messages**  

---

## ⚙️ Tech Stack

| Layer | Technology |
|-------|-------------|
| Language | Java 17+ |
| Framework | Spring WebSocket |
| API | Jakarta WebSocket (for client) |
| IDE | Eclipse |
| Build Tool | Maven |

---

## 🧩 How It Works

### 🖥️ Backend (Server)

The backend uses **Spring WebSocket** with `TextWebSocketHandler`:
```java
@Component
public class ChatHandler extends TextWebSocketHandler {
    private final Map<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Example: broadcast message to all users in the same room
        for (WebSocketSession s : rooms.get("default")) {
            if (s.isOpen() && !s.equals(session)) { // avoid echoing back to sender
                s.sendMessage(message);
            }
        }
    }
}
````

✅ Each client message is sent to **other clients** but **not echoed back** to the sender.

---

### 💻 Client (Java)

The client connects using the Jakarta WebSocket API:

```java
@ClientEndpoint
public class ChatClient {
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to server");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }
}
```

Example connection URL:

```java
ws://localhost:8080/chat
```

---

## 🧠 Example Output

**Client 1**

```
Connected to server
You: Hello!
```

**Client 2**

```
Connected to server
Client 1: Hello!
You: Hi there!
```

**Client 1 (after Client 2 sends message)**

```
Client 2: Hi there!
```

✅ Each client receives only other clients’ messages.

---

## ▶️ How to Run

### 1️⃣ Start the Backend Server

1. Open `backend` project in Eclipse.
2. Run the Spring Boot main class (e.g., `WebSocketServerApplication.java`).
3. The server starts at `http://localhost:8080`.

### 2️⃣ Start the Client

1. Open `clientApp` project.
2. Run the main client class containing `@ClientEndpoint`.
3. It connects automatically to the WebSocket endpoint (`ws://localhost:8080/chat`).

Run multiple clients (in different terminals) to test live chat!

---

## 🧹 Future Enhancements

* Add username-based identification
* Show timestamp with messages
* Store message history
* Add Web or GUI frontend

---

### 👨‍💻 Author

**Hardhik Bangera**
💼 GitHub: [@hardhikbangera](https://github.com/hardhikbangera)

---

✨ *Spring-powered real-time WebSocket chat — simple, fast, and scalable!* ✨


