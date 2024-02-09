import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './UserEdit.css';
import AuthFilter from '../../services/AuthFilter';

const getToken = () => {
  return localStorage.getItem('Token');
};

const getUserDetails = async (userId) => {
  const USERS_URL = `http://localhost:8081/api/users/${userId}`;
  const response = await fetch(USERS_URL, {
    method: 'GET',
    headers: {
      Authorization: 'Bearer ' + getToken(),
    },
  });

  if (!response.ok) {
    throw new Error('Failed to fetch user details');
  }

  return response.json();
};

const updateUserDetails = async (userId, updatedDetails) => {
  const UPDATE_USER_URL = `http://localhost:8081/api/users/${userId}/update`;
  const response = await fetch(UPDATE_USER_URL, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + getToken(),
    },
    body: JSON.stringify(updatedDetails),
  });

  if (!response.ok) {
    throw new Error('Failed to update user details');
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

const UserEdit = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [userDetails, setUserDetails] = useState({});
  const [updatedDetails, setUpdatedDetails] = useState({});
  const [roles, setRoles] = useState([]); 
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        const details = await getUserDetails(id);
        setUserDetails(details);
        setUpdatedDetails(details);

        const rolesData = await getUserRoles();
        setRoles(rolesData);
      } catch (error) {
        setError(error.message);
      }
    };

    fetchUserDetails();
  }, [id]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
  
    // If the input is the role dropdown, update the selected role directly
    if (name === "role") {
      setUpdatedDetails((prevDetails) => ({
        ...prevDetails,
        roles: [{ id: value }], // Assuming roles is an array with objects like { id, name }
      }));
    } else {
      // Otherwise, update other details normally
      setUpdatedDetails((prevDetails) => ({
        ...prevDetails,
        [name]: value,
      }));
    }
  };
  

  const handleUpdateUser = async () => {
    try {
      await updateUserDetails(id, updatedDetails);
      navigate(`/admin/users`);
    } catch (error) {
      setError(error.message);
    }
  };

  return (
    <div>
      <h2>Edit User Details</h2>
      {error && <p>Error: {error}</p>}
      {
        userDetails && (
          <div>
            <div>
              <label>Username:</label>
              <input
                type="text"
                name="username"
                value={updatedDetails.username || ''}
                onChange={handleInputChange}
              />
            </div>
            <div>
              <label>Email:</label>
              <input
                type="text"
                name="email"
                value={updatedDetails.email || ''}
                onChange={handleInputChange}
              />
            </div>
            <div>
              <label>Role:</label>
              <select
                name="role"
                value={updatedDetails.roles && updatedDetails.roles[0].id || ''}
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
          </div>
        )
      }

      <button onClick={handleUpdateUser}>Update User</button>
    </div>
  );
};

export default AuthFilter(UserEdit);