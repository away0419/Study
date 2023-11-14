import React from "react";
import Super from "./Super";

class App extends React.Component {
  render() {
    const flag = true;
    const arr = [1, 2, 3];
    const obj = { name: "name", email: "email" };
    const number = 20;

    return (
      <div>
        This is App.
        <Super title="React" flag={flag} arr={arr} obj={obj} number={number}>
          app text
        </Super>
      </div>
    );
  }
}

export default App;
