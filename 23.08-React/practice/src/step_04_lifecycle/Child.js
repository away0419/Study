import React, { Component } from "react";

export default class Child extends Component {
  constructor(props) {
    super(props);
    this.state = {
      inputData: props.children,
    };
  }
  render() {
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
