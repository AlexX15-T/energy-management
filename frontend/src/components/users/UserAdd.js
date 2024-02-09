import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './UserAdd.css';
import AuthFilter from '../../services/AuthFilter';

const getToken = () => {
  return localStorage.getItem('Token');
};

const addUser = async (userDetails) => {
  const ADD_USER_URL = 'http://localhost:8081/api/users/add';
  const response = await fetch(ADD_USER_URL, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + getToken(),
    },
    body: JSON.stringify(userDetails),
  });

  if (!response.ok) {
    throw new Error('Failed to add user');
  }

  //add user in second db
    const ADD_USER_URL2 = 'http://localhost:8085/api/users/add';
    
    const userId = (await response.json());

    const userDb2 = {
        "id": userId,
    };

    const response2 = await fetch(ADD_USER_URL2, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },

      body: JSON.stringify(userDb2),
    });

    if (!response2.ok) {
        throw new Error('Failed to add user in db2 with id ' + userId);
    }

  return response.json();
};

const getUserRoles = async () => {
  const ROLES_URL = 'http://localhost:8081/api/users/roles';
  const response = await fetch(ROLES_URL, {
    method: 'GET',
    headers: {
      Authorization: 'Bearer ' + getToken(),
    },
  });

  if (!response.ok) {
    throw new Error('Failed to fetch user roles');
  }

  return response.json();
};

const UserAdd = () => {
  const navigate = useNavigate();
  const [userDetails, setUserDetails] = useState({
    username: '',
    email: '',
    password: '', // Add the password field
    roles: [],
  });
  const [roles, setRoles] = useState([]); 
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchRoles = async () => {
      try {
        const rolesData = await getUserRoles();
        setRoles(rolesData);
      } catch (error) {
        setError(error.message);
      }
    };

    fetchRoles();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
  
    // If the input is the role dropdown, update the selected role directly
    if (name === "roles") {
      setUserDetails((prevDetails) => ({
        ...prevDetails,
        [name]: [{ id: value }], // Assuming roles is an array with objects like { id, name }
      }));
    } else {
      // Otherwise, update other details normally
      setUserDetails((prevDetails) => ({
        ...prevDetails,
        [name]: value,
      }));
    }
  };
  

  const handleAddUser = async () => {
    try {
      await addUser(userDetails);
      navigate(`/admin/users`);
    } catch (error) {
      setError(error.message);
    }
  };

  return (
    <div>
      <h2>Add User</h2>
      {error && <p>Error: {error}</p>}
      <div>
        <label>Username:</label>
        <input
          type="text"
          name="username"
          value={userDetails.username}
          onChange={handleInputChange}
        />
      </div>
      <div>
        <label>Email:</label>
        <input
          type="text"
          name="email"
          value={userDetails.email}
          onChange={handleInputChange}
        />
      </div>
      <div>
        <label>Password:</label>
        <input
          type="password"
          name="password"
          value={userDetails.password}
          onChange={handleInputChange}
        />
      </div>
      <div>
        <label>Role:</label>
        <select
          name="roles"
          value={userDetails.roles[0] && userDetails.roles[0].id || ''}
          onChange={handleInputChange}
        >
          <option value="" disabled>Select Role</option>
          {roles.map(role => (
            <option key={role.id} value={role.id}>
              {role.name}
            </option>
          ))}
        </select>
      </div>
      <button onClick={handleAddUser}>Add User</button>
    </div>
  );
};

export default AuthFilter(UserAdd);
