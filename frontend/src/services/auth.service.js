import axios from "axios";
import Cookies from "js-cookie";

// const API_USERS_DEFAULT_URL = "http://localhost:8080/api";
// const API_DEVICES_DEFAULT_URL = "http://localhost:8081/api/";

// const API_USERS_DOCKER_URL = "http://user-microservice:8080/api";
// const API_DEVICES_DOCKER_URL = "http://device-microservice:8081/api";

const API_URL = "http://localhost:8081/api/auth/";


const register = (username, email, password) => {
  return axios.post(API_URL + "signup", {
    username,
    email,
    password,
  });
};

const login = (username, password) => {
  return axios
    .post(API_URL + "signin", {
      username,
      password,
    },{
      withCredentials: true,
    })
    .then((response) => {
      if (response.data.username) {
        localStorage.setItem("user", JSON.stringify(response.data));
        const token = response.data.token;
        console.log("Token from login is: " + token);
        localStorage.setItem("Token", token);
      }

      return response.data;
    });
};

const logout = () => {
  localStorage.removeItem("user");
  return axios.post(API_URL + "signout").then((response) => {
    return response.data;
  });
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
  register,
  login,
  logout,
  getCurrentUser,
  // API_USERS_DEFAULT_URL,
  // API_DEVICES_DEFAULT_URL,
  // API_USERS_DOCKER_URL,
  // API_DEVICES_DOCKER_URL,
}

export default AuthService;
