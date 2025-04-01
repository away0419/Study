import React, { useEffect, useRef } from "react";
import Hls from "hls.js";

const HlsPlayer = ({ videoSrc }) => {
  const videoRef = useRef(null);

  useEffect(() => {
    if (Hls.isSupported()) {
      const hls = new Hls();
      hls.loadSource(videoSrc);
      hls.attachMedia(videoRef.current);
    }
  }, [videoSrc]);

  return <video ref={videoRef} controls style={{ width: "100%" }} />;
};

export default HlsPlayer;
