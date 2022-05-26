import { instance } from "@/components/axios";
import { Storage } from "@/components/storage";
import { NextPage } from "next";
import Router from "next/router";
import { useState } from "react";

const Login: NextPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const onClick = async () => {
    if (username == "" || password == "") return;
    const response = await instance.post("account/login", {
      username,
      password,
    });

    const user = response.data;
    Storage.userToken = user.token;
    Storage.username = user.username;

    Router.push("/live/room");
  };

  return (
    <div className="flex justify-center items-center w-full h-full">
      <div className="flex flex-col w-1/3 h-1/3 bg-gray-400 rounded-lg">
        <div className="flex justify-center items-center w-full h-full">
          <div className="mr-4">
            <label className="inline-block">Login</label>
            <input
              className="block rounded-lg"
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}></input>
          </div>
          <div className="ml-4">
            <label className="inline-block">Password</label>
            <input
              className="block rounded-lg"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}></input>
          </div>
        </div>
        <div className="flex justify-center items-center w-full h-full">
          <button
            className="bg-blue-600 pl-4 pr-4 rounded-lg"
            onClick={onClick}>
            Login
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;
