import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const ExistingUserFilter = (WrappedComponent) => {
  return function WithAuthorization(props) {
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem("user"));

    // useEffect(() => {
    //   if (!user) {
    //     // User does not exist, perform programmatic redirect
    //     navigate("/login");
    //   }
    // }, [user, navigate]);

    // Render the wrapped component or null
    return user ? <WrappedComponent {...props} /> : null;
  };
};

export default ExistingUserFilter;
