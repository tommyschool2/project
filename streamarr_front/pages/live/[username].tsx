import { Storage } from "@/components/storage";
import { Ws } from "@/components/ws";
import type { NextPage } from "next";
import Router from "next/router";
import Script from "next/script";
import { RefObject, useEffect, useRef, useState } from "react";

const Live: NextPage = () => {
  const [message, setMessage] = useState("");
  const [chatMessages, setChatMessages] = useState([]) as any;

  useEffect(() => {
    if (!Storage.userToken) {
      Router.push("/login");
      return;
    }

    if (!Ws.ws) {
      Ws.ws = new WebSocket("wss://projectback.herokuapp.com/chatws");
      Ws.ws.onopen = function () {
        console.log("Connection opened");

        Ws.ws.onmessage = function (data) {
          setChatMessages((prevItems: any) => [...prevItems, data.data]);
        };
      };
    }
  }, []);

  const videoPlayer: RefObject<HTMLVideoElement> = useRef(null);

  function setupMediaPlayer() {
    if (Hls.isSupported()) {
      const hls = new Hls();
      console.log(hls.attachMedia(videoPlayer.current!));
      console.log(videoPlayer.current);
      hls.on(Hls.Events.MEDIA_ATTACHED, function () {
        console.log("video and hls.js are now bound together !");
        hls.loadSource(
          "https://multiplatform-f.akamaihd.net/i/multi/will/bunny/big_buck_bunny_,640x360_400,640x360_700,640x360_1000,950x540_1500,.f4v.csmil/master.m3u8"
        );
        hls.on(Hls.Events.MANIFEST_PARSED, function (event, data: any) {
          videoPlayer.current!.play();
        });
      });
    }
  }

  const sendMessage = () => {
    if (!message) return;

    Ws.ws.send(Storage.username + ": " + message);
  };

  return (
    <>
      <div className="flex w-full h-full">
        <video
          ref={videoPlayer}
          muted
          controls
          className="bg-black w-10/12 h-10/12"></video>
        <div
          className="flex flex-col w-full h-full"
          style={{ backgroundColor: "#282b30" }}>
          <div className="flex justify-center items-center w-full h-full text-white">
            <div className="w-11/12 h-full">
              {chatMessages.map((message: string) => (
                <p key={message}>{message}</p>
              ))}
            </div>
          </div>
          <div className="flex" style={{ backgroundColor: "#1e2124" }}>
            <input
              type="text"
              placeholder="message"
              className="w-full"
              onChange={(e) => setMessage(e.target.value)}></input>
            <button
              className="pt-2 pb-2 pl-5 pr-5"
              style={{ backgroundColor: "#7289da" }}
              onClick={sendMessage}>
              Send
            </button>
          </div>
        </div>
      </div>

      <Script
        src="https://cdn.jsdelivr.net/npm/hls.js@latest"
        strategy="afterInteractive"
        onLoad={setupMediaPlayer}></Script>
    </>
  );
};

export default Live;
