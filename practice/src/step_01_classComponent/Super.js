import React, { Component } from "react";
import Child from "./Child";

export default class Super extends Component {
  render() {
    return (
      <div>
        This is Super.
        <div>
          {this.props.title}
          {this.props.flag}
          {this.props.number}
          <br />
          {this.props.children}

          <ul>
            {this.props.arr.map((value) => (
              <li>{value}</li>
            ))}
          </ul>
          <ul>
            {Object.keys(this.props.obj).map((key, index) => (
              <li>
                {key} : {index}
              </li>
            ))}
          </ul>
          <ul>
            {Object.entries(this.props.obj).map(([key, value], idx) => (
              <li>
                {idx} [ {key} : {value} ]
              </li>
            ))}
          </ul>

          <ul>
            {this.props.arr?.map((value, idx) => (
              <li key={idx}>
                {idx} - {value}
              </li>
            ))}
          </ul>
        </div>
        <Child></Child>
      </div>
    );
  }
}
