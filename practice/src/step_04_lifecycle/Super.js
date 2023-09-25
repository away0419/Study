import React, { Component } from "react";
import Child from "../step_03_simple/Child";

export default class Super extends Component {
  state = {
    selectedItem: null,
  };
  componentDidMount() {
    console.log("%c%s", "color:blue", "Super componentDidMount");
  }
  handleClick(data) {
    this.state.selectedItem.name = data;
    this.setState({ selectedItem: this.state.selectedItem });
  }
  render() {
    console.log("%c%s", "color:blue", "Super render()");
    return (
      <div>
        Super
        <ul>
          {this.props.items.map((item) => (
            <li key={item.id}>
              <a
                href="#"
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
        <Child onClickEdit={(data) => this.handleClick(data)}>
          {this.state.selectedItem?.name}
        </Child>
      </div>
    );
  }
}
