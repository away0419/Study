import React, { Component } from "react";

export default class Child extends Component {
  constructor(props) {
    console.log("%c%s", "color:green", "Child constructor");
    super(props);
    this.state = {
      inputData: props.children,
    };
  }
  componentDidMount() {
    console.log("%c%s", "color:green", "Child componentDidMount");
  }
  render() {
    console.log("%c%s", "color:green", "Child render()");
    return (
      <div>
        Child {this.props.children}
        <br />
        Child {this.state.inputData}
        <br />
        <input
          type="text"
          value={this.state.inputData}
          onChange={(event) => {
            this.setState({ inputData: event.target.value });
          }}
        ></input>
        <input
          type="button"
          value="Edit"
          onClick={() => {
            this.props.onClickEdit(this.state.inputData);
          }}
        ></input>
      </div>
    );
  }
}
