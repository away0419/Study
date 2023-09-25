import React from "react";
import Super from "./Super";

function App() {
  const items = [
    { id: 1, name: "react" },
    { id: 2, name: "redux" },
  ];
  return (
    <div>
      App
      <Super items={items}></Super>
    </div>
  );
}

export default App;
