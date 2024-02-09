import React, { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import "./components/users/UsersHome.css";
import "./components/users/UserDetails.css";
import "./components/users/UserEdit.css";
import "./components/devices/DeviceEdit.css";
import "./components/devices/DeviceDetails.css";
import "./components/devices/DevicesHome.css";
import "./components/devices/DeviceAdd.css";
import ChatIcon from '@mui/icons-material/Chat';

import AuthService from "./services/auth.service";

import Login from "./components/Login";
import Register from "./components/Register";
import Home from "./components/Home";
import Profile from "./components/Profile";
import BoardUser from "./components/BoardUser";
import BoardModerator from "./components/BoardModerator";
import BoardAdmin from "./components/BoardAdmin";
import UsersHome from "./components/users/UsersHome";
import DevicesHome from "./components/devices/DevicesHome";
import UserDetails from "./components/users/UserDetails";
import DeviceDetails from "./components/devices/DeviceDetails";
import DeviceEdit from "./components/devices/DeviceEdit";
import DeviceAdd from "./components/devices/DeviceAdd";
import UserAdd from "./components/users/UserAdd";
import ChatPage from "./components/chat/ChatPage";

// import AuthVerify from "./common/AuthVerify";
import EventBus from "./common/EventBus";
import UserEdit from "./components/users/UserEdit";

const App = () => {
  const [showModeratorBoard, setShowModeratorBoard] = useState(false);
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);
      setShowModeratorBoard(user.roles.includes("ROLE_MODERATOR"));
      setShowAdminBoard(user.roles.includes("ROLE_ADMIN"));
    }

    EventBus.on("logout", () => {
      logOut();
    });

    return () => {
      EventBus.remove("logout");
    };
  }, []);

  const logOut = () => {
    AuthService.logout();
    setShowModeratorBoard(false);
    setShowAdminBoard(false);
    setCurrentUser(undefined);
  };

  return (
    <div>
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <Link to={"/"} className="navbar-brand">
          Energy Management System
        </Link>
        <div className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/home"} className="nav-link">
              Home
            </Link>
          </li>

          {showModeratorBoard && (
            <li className="nav-item">
              <Link to={"/mod"} className="nav-link">
                Moderator Board
              </Link>
            </li>
          )}

          {showAdminBoard && (
            <li className="nav-item">
              <Link to={"/admin"} className="nav-link">
                Admin Board
              </Link>

              <Link to={"/admin/users"} className="nav-link">
                Modify Users
              </Link>

              <Link to={"/admin/devices"} className="nav-link">
                Modify Devices
              </Link>
            </li>
          )}

          {currentUser && (
            <li className="nav-item">
              <Link to={"/user"} className="nav-link">
                User
              </Link>
            </li>
          )}

          {
            currentUser && (
              <li className="nav-item">
                <Link to={"/chat"} className="nav-link">
                  <ChatIcon /> Chat
                </Link>
              </li>
            )
          }
        </div>

        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/profile"} className="nav-link">
                {currentUser.username}
              </Link>
            </li>
            <li className="nav-item">
              <a href="/login" className="nav-link" onClick={logOut}>
                LogOut
              </a>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/login"} className="nav-link">
                Login
              </Link>
            </li>

            <li className="nav-item">
              <Link to={"/register"} className="nav-link">
                Sign Up
              </Link>
            </li>
          </div>
        )}
      </nav>

      <div className="container mt-3">
        <Routes>
          <Route exact path={"/"} element={<Home />} />
          <Route exact path={"/home"} element={<Home />} />
          <Route exact path={"/login"} element={<Login />} />
          <Route exact path={"/register"} element={<Register />} />
          <Route exact path={"/profile"} element={<Profile />} />
          <Route exact path={"/user"} element={<BoardUser />} />
          <Route exact path={"/mod"} element={<BoardModerator />} />
          <Route exact path={"/admin"} element={<BoardAdmin />} />
          <Route exact path={"/chat"} element={<ChatPage />} />
          <Route exact path={"/admin/users"} element={<UsersHome />} />
          <Route exact path={"/admin/users/:id/details"} element = {<UserDetails />} />
          <Route exact path={"/admin/users/:id/edit"} element = {<UserEdit />} />
          <Route exact path={"/admin/users/add"} element={<UserAdd />} />
          <Route exact path={"/admin/devices"} element={<DevicesHome />} />
          <Route exact path={"/admin/devices/:id/details"} element={<DeviceDetails />} />
          <Route exact path={"/admin/devices/:id/edit"} element={<DeviceEdit />} />
          <Route exact path={"/admin/devices/add"} element={<DeviceAdd />} />
        </Routes>
      </div>

      {/* <AuthVerify logOut={logOut}/> */}
    </div>
  );
};

export default App;
