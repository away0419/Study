import React, { Component } from "react";
import ChildChild from "./ChildChild";

export default class Child extends Component {
  render() {
    return (
      <div>
        This is Child
        <ChildChild></ChildChild>
      </div>
    );
  }
}
