import React, { useState } from "react";
import "../App.css";

function JoinScreen({ onJoin }) {
  const [name, setName] = useState("");

  const handleJoin = () => {
    if (name.trim()) onJoin(name.trim());
  };

  return (
    <div className="join-page">
      <div className="join-card">
        <h2>Welcome to ChatCord</h2>
        <input
          type="text"
          placeholder="Enter your username..."
          value={name}
          onChange={(e) => setName(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && handleJoin()}
        />
        <button onClick={handleJoin}>Join</button>
      </div>
    </div>
  );
}

export default JoinScreen;
