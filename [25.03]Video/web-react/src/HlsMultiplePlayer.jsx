import React, { useState } from "react";
import HlsPlayer from "./HlsPlayer";

const HlsMultiplePlayer = () => {
  const [quality, setQuality] = useState("auto");

  const videoSources = {
    auto: "http://localhost:8080/hls/multiple/test/master.m3u8",
    "360p": "http://localhost:8080/hls/multiple/test/360p.m3u8",
    "480p": "http://localhost:8080/hls/multiple/test/480p.m3u8",
    "720p": "http://localhost:8080/hls/multiple/test/720p.m3u8",
    "1080p": "http://localhost:8080/hls/multiple/test/1080p.m3u8",
  };

  return (
    <div>
      <select onChange={(e) => setQuality(e.target.value)}>
        <option value="auto">자동 (Adaptive Streaming)</option>
        <option value="360p">360p</option>
        <option value="480p">480p</option>
        <option value="720p">720p</option>
        <option value="1080p">1080p</option>
      </select>
      <HlsPlayer videoSrc={videoSources[quality]} />
    </div>
  );
};

export default HlsMultiplePlayer;
