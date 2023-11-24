import React, { Component } from "react";
import Super from "./Super";

export default class App extends Component {
  items = [
    { id: 1, name: "React" },
    { id: 2, name: "Redux" },
  ];
  render() {
    return (
      <div>
        App
        <Super items={this.items}></Super>
      </div>
    );
  }
}
