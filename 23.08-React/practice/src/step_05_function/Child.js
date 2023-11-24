import { useState } from "react";

function Child() {
  const [inputData, setInputData] = useState("");
  return (
    <div>
      Child
      <div>
        <input type="text" value={inputData}></input>
      </div>
    </div>
  );
}

export default Child;
