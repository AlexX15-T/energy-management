import React, { useState, useEffect } from "react";

import UserService from "../services/user.service";
import EventBus from "../common/EventBus";
import AuthFilter from '../services/AuthFilter';

const BoardAdmin = () => {
  const [content, setContent] = useState("");

  useEffect(() => {
    UserService.getAdminBoard().then(
      (response) => {
        response.text().then((text) => {
          console.log('BoardAdmin response text:', text);
          setContent(text);
        });
      },
      (error) => {
        const _content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        console.log('BoardAdmin error:', error);
        setContent(_content);

        if (error.response && error.response.status === 401) {
          EventBus.dispatch("logout");
        }
      }
    );
  }, []);

  return (
    <div className="container">
      <header className="jumbotron">
        <h3>{content}</h3>
      </header>
    </div>
  );
};

export default AuthFilter(BoardAdmin);
