import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import AuthFilter from '../../services/AuthFilter';

const getToken = () => {
  return localStorage.getItem('Token');
};

const getUsersList = async () => {
  const USERS_URL = "http://localhost:8081/api/users";
  const response = await fetch(USERS_URL + "/all", {
    method: 'GET',
    headers: {
      Authorization: 'Bearer ' + getToken(),
    },
  });

  if (!response.ok) {
    throw new Error('Failed to fetch users');
  }

  return response.json();
};

const handleDelete = async (userId) => {
  console.log(`Delete user with ID ${userId}`);

  const API_USERS_DEFAULT_URL = 'http://localhost:8081/api/';
  const API_DEVICES_DEFAULT_URL = 'http://localhost:8085/api/';

  const DELETE_USER_URL = API_USERS_DEFAULT_URL + `users/${userId}/delete`;

  //delete devices first
  const DELETE_USER_DEVICES_URL = API_DEVICES_DEFAULT_URL + `devices/users/${userId}/delete`;

  try {
    const response = await fetch(DELETE_USER_DEVICES_URL, {
      method: 'DELETE',
      headers: {
      },
    });

    if (!response.ok) {
      throw new Error('Failed to delete user devices');
    }

  } catch (error) {
    console.error('Error deleting user devices:', error.message);
  }

  //delete user from db2
  const DELETE_USER_URL2 = API_DEVICES_DEFAULT_URL + `users/${userId}/delete`;

  try {
    const response = await fetch(DELETE_USER_URL2, {
      method: 'DELETE',
      headers: {
      },
    });

    if (!response.ok) {
      throw new Error('Failed to delete user from db2');
    }

  } catch (error) {
    console.error('Error deleting user from db2:', error.message);
  }

  //delete user from db1
  try {
    const response = await fetch(DELETE_USER_URL, {
      method: 'DELETE',
      headers: {
        Authorization: 'Bearer ' + getToken(),
      },
    });

    if (!response.ok) {
      throw new Error('Failed to delete user');
    }

    window.location.reload();
  } catch (error) {
    console.error('Error deleting user:', error.message);
  }
};


const UsersHome = () => {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const userList = await getUsersList();
        setUsers(userList);
      } catch (error) {
        setError(error.message);
      }
    };

    fetchUsers();
  }, []);

  return (
    <div>
      <h1>Users List</h1>
      {error && <p>Error: {error}</p>}
      <Link to="/admin/users/add" className="add-user-button">Add User</Link>
      <table>
        <thead>
          <tr>
            <th>Username</th>
            <th>Email</th>
            <th>Role</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {users.map(user => (
            <tr key={user.id}>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td>{user.roles[0].name.split("ROLE_")}</td>
              <td>
                <Link to={`/admin/users/${user.id}/details`}>Show Details</Link>
                <button onClick={() => handleDelete(user.id)}>Delete</button>
                <Link to={`/admin/users/${user.id}/edit`}>Edit</Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AuthFilter(UsersHome);
