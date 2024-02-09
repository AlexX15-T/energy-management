import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './DeviceAdd.css';
import AuthFilter from '../../services/AuthFilter';

const addNewDevice = async (newDevice) => {
  const ADD_DEVICE_URL =  "http://localhost:8085/api/devices/add";
  const response = await fetch(ADD_DEVICE_URL, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + getToken(),
    },
    body: JSON.stringify(newDevice),
  });

  if (!response.ok) {
    throw new Error('Failed to add a new device');
  }

  return response.json();
};

const getToken = () => {
    return localStorage.getItem('Token');
  };

const getAvailableUsers = async () => {
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

const DeviceAdd = () => {
    const navigate = useNavigate();
  const [newDevice, setNewDevice] = useState({});
  const [availableUsers, setAvailableUsers] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const users = await getAvailableUsers();
        setAvailableUsers(users);
      } catch (error) {
        setError(error.message);
      }
    };

    fetchUsers();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewDevice((prevDevice) => ({ ...prevDevice, [name]: value }));
  };

  const handleAddDevice = async () => {
    try {
      await addNewDevice(newDevice);
      navigate('/admin/devices')
      console.log('New device added successfully');
    } catch (error) {
      setError(error.message);
    }
  };

  return (
    <div>
      <h2>Add New Device</h2>
      {error && <p>Error: {error}</p>}
      <div>
        <div>
          <label>Description:</label>
          <input
            type="text"
            name="description"
            value={newDevice.description || ''}
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label>Address:</label>
          <input
            type="text"
            name="address"
            value={newDevice.address || ''}
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label>Maximum Hourly Energy Consumption:</label>
          <input
            type="number"
            name="maximumHourlyEnergyConsumption"
            value={newDevice.maximumHourlyEnergyConsumption || ''}
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label>User:</label>
          <select
            name="userId"
            value={newDevice.userId || ''}
            onChange={handleInputChange}
          >
            <option value="" disabled>Select User</option>
            {availableUsers.map(user => (
              <option key={user.id} value={user.id}>
                {user.username}
              </option>
            ))}
          </select>
        </div>
        <button onClick={handleAddDevice}>Add Device</button>
      </div>
    </div>
  );
};

export default AuthFilter(DeviceAdd);
