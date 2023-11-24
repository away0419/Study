import React, { Component } from "react";
import Super from "./Super";

export default class App extends Component {
  constructor(props) {
    super(props);
    console.log("%c%s", "color:red", "App constructor");
  }
  componentDidMount() {
    console.log("%c%s", "color:red", "App componentDidMount");
  }
  items = [
    { id: 1, name: "React" },
    { id: 2, name: "Redux" },
  ];
  render() {
    console.log("%c%s", "color:red", "App render()");
    return (
      <div>
        App
        <Super items={this.items}></Super>
      </div>
    );
  }
}
