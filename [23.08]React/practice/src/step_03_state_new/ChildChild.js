import React, { Component } from "react";

export default class ChildChild extends Component {
  constructor(props) {
    super(props);
    this.state = { inputData: props.children };
  }
  render() {
    return (
      <>
        <div>This is ChildChild</div>
        <div>{this.props.children}</div>
        <div>{this.state.inputData}</div>
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
          onClick={function () {
            this.props.onClickEdit(this.state.inputData);
          }.bind(this)}
        ></input>
      </>
    );
  }
}
