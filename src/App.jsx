import React, { useState } from "react";
import JoinScreen from "./components/JoinScreen";
import ChatRoom from "./components/ChatRoom";
import "./App.css";

function App() {
  const [username, setUsername] = useState("");

  return (
    <div className="app">
      {!username ? (
        <JoinScreen onJoin={setUsername} />
      ) : (
        <ChatRoom username={username} />
      )}
    </div>
  );
}

export default App;
