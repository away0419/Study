import React from "react";
import Super from "./Super";
import { brown } from "@mui/material/colors";

class App extends React.Component {
  items = [
    { id: 1, name: "React", path: "/react" },
    { id: 2, name: "Redux", path: "/redux" },
  ];
  render() {
    return (
      <div style={{ background: "brown" }}>
        This is App.
        <Super title="React" items={this.items}>
          app text
        </Super>
      </div>
    );
  }
}

export default App;
