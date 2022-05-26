import axios from "axios";

export const instance = axios.create({
  baseURL: "https://projectback.herokuapp.com",
});
