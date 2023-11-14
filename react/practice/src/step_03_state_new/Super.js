import React, { Component } from "react";
import Child from "./Child";

export default class Super extends Component {
  state = {
    selectedItem: null,
  };

  render() {
    return (
      <div className="super" style={{ background: "orange" }}>
        This is Super.
        <ul style={{ display: "flex", backgroundColor: "red" }}>
          {this.props.items.map((item) => (
            <li key={item.id}>
              <a
                href={item.path}
                onClick={(event) => {
                  event.preventDefault();
                  this.setState({ selectedItem: item });
                }}
              >
                {item.name}
              </a>
            </li>
          ))}
        </ul>
        <Child
          selectedItem={this.state.selectedItem}
          onClickEdit={(data) => {
            this.state.selectedItem.name = data;
            this.setState({ selectedItem: this.state.selectedItem });
          }}
        ></Child>
      </div>
    );
  }
}
