import axios from "axios";

const API_URL = "http://localhost:8081/api/test/";
const USERS_URL = "http://localhost:8081/api/users/";

const getPublicContent = () => {
  return axios.get(API_URL + "all");
};

const getUserBoard = () => {
  return fetch(API_URL + "user", {
    method: 'GET',
    include: 'credentials',
    headers: {
       Authorization: 'Bearer ' + getToken(),
    },
  });
};

const getModeratorBoard = () => {
  return fetch(API_URL + "mod", {
    method: 'GET',
    include: 'credentials',
    headers: {
       Authorization: 'Bearer ' + getToken(),
    },
  });
};

const getAdminBoard = () => {
  return fetch(API_URL + "admin", {
    method: 'GET',
    credentials: 'include',
    headers: {
       Authorization: 'Bearer ' + getToken(),
    },
  });
};

const getToken = () => {
  return localStorage.getItem('Token');
};

const getUsersList = () => {
  return fetch(USERS_URL + "/all", {
    method: 'GET',
    headers: {
       Authorization: 'Bearer ' + getToken(),
    },
  });
};

const UserService = {
  getPublicContent,
  getUserBoard,
  getModeratorBoard,
  getAdminBoard,
}

export default UserService;
