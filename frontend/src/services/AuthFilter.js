import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const AuthFilter = (WrappedComponent) => {
  return function WithAuthorization(props) {
    const navigate = useNavigate();

    const user = JSON.parse(localStorage.getItem("user"));

    // useEffect(() => {
    //   const user = JSON.parse(localStorage.getItem("user"));
    //   if (!user) {
    //     navigate("/login");
    //   }
    //   else if (user.roles[0] === "ROLE_USER" && props.path === "/user") {
    //     navigate("/user");
    //   }
    //   else if (user.roles[0] === "ROLE_USER" && props.path === "/profile") {
    //     navigate("/profile");
    //   }
    // }, [user, navigate]);

    // Render the wrapped component or null
    return user ? <WrappedComponent {...props} /> : null;
  };
};

export default AuthFilter;
