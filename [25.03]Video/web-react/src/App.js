import logo from "./logo.svg";
import "./App.css";
import HlsPlayer from "./HlsPlayer";
import HlsMultiplePlayer from "./HlsMultiplePlayer";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
        <HlsPlayer videoSrc="http://localhost:8080/hls/basic/test/output.m3u8" />
        <HlsMultiplePlayer />
      </header>
    </div>
  );
}

export default App;
