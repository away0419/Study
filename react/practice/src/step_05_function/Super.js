import { useState } from "react";

function Super(props) {
  const [selectedItem, setSelectedItem] = useState(null);

  return (
    <div>
      Super
      <ul>
        {props.items.map((item) => (
          <li key={item.id}>{item.name}</li>
        ))}
      </ul>
    </div>
  );
}

export default Super;
