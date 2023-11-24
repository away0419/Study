import React, { Component } from "react";
import ChildChild from "./ChildChild";

export default class Child extends Component {
  handleClickEdit(data) {
    this.props.onClickEdit(data);
  }
  render() {
    return (
      <div>
        This is Child
        {this.props.selectedItem ? (
          <>
            <p>{this.props.selectedItem.name} selected.</p>
            <ChildChild onClickEdit={this.handleClickEdit.bind(this)}>
              {this.props.selectedItem.name}
            </ChildChild>
          </>
        ) : (
          <>
            <p>No selected</p>
            <ChildChild onClickEdit={this.handleClickEdit.bind(this)}>
              This is children
            </ChildChild>
          </>
        )}
      </div>
    );
  }
}
