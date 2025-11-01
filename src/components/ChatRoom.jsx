import React, { useEffect, useRef, useState } from "react";
import "../App.css";

function ChatRoom({ username }) {
  const [messages, setMessages] = useState([]);
  const [users, setUsers] = useState(new Set());
  const [text, setText] = useState("");
  const ws = useRef(null);
  const endRef = useRef(null);

  useEffect(() => {
    ws.current = new WebSocket("ws://localhost:8080/chat");

    ws.current.onopen = () => {
      ws.current.send(username);
    };

    ws.current.onmessage = (event) => {
      const msg = event.data;

      // Track joins & leaves for sidebar
      if (msg.includes("joined the chat")) {
        const user = msg.replace(" joined the chat", "").trim();
        setUsers((prev) => new Set([...prev, user]));
      } else if (msg.includes("left the chat")) {
        const user = msg.replace(" left the chat", "").trim();
        setUsers((prev) => {
          const copy = new Set(prev);
          copy.delete(user);
          return copy;
        });
      }

      setMessages((prev) => [...prev, msg]);
    };

    ws.current.onclose = () => console.log("Disconnected");

    return () => ws.current.close();
  }, [username]);

  useEffect(() => {
    endRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  const sendMessage = () => {
    if (text.trim()) {
      ws.current.send(text);
      setText("");
    }
  };

  return (
    <div className="chat-wrapper">
      {/* Sidebar */}
      <div className="sidebar">
        <h3>Online Users</h3>
        <ul>
          {[...users].map((user, idx) => (
            <li key={idx} className={user === username ? "me" : ""}>
              {user}
            </li>
          ))}
        </ul>
      </div>

      {/* Chat area */}
      <div className="chat-panel">
        <div className="chat-header"># general</div>

        <div className="messages">
          {messages.map((msg, i) => {
            const [user, ...content] = msg.split(" : ");
            return (
              <div key={i} className="message">
                {content.length > 0 ? (
                  <>
                    <span className="user">{user}</span>
                    <span className="text">{content.join(" : ")}</span>
                  </>
                ) : (
                  <div className="system">{msg}</div>
                )}
              </div>
            );
          })}
          <div ref={endRef}></div>
        </div>

        <div className="input-area">
          <input
            type="text"
            placeholder="Message #general"
            value={text}
            onChange={(e) => setText(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && sendMessage()}
          />
          <button onClick={sendMessage}>Send</button>
        </div>
      </div>
    </div>
  );
}

export default ChatRoom;
