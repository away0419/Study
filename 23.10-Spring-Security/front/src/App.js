import logo from "./logo.svg";
import "./App.css";

function App() {
  return (
    <div className="App">
      <img src={logo} className="App-logo" alt="logo" />
      <a href="http://localhost:8080/oauth2/authorization/google">
        Learn React
      </a>
      <br />
      <a href="http://localhost:8080/oauth2/authorization/kakao">Learn kakao</a>
      <br />
      <a href="http://localhost:8080/oauth2/authorization/naver">Learn naver</a>
    </div>
  );
}

export default App;
