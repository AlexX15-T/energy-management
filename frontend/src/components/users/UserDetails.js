import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import './UserDetails.css';
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

const getUserDevices = async (userId) => {
  const GET_USER_DEVICES_URL = `http://localhost:8085/api/devices/users/${userId}`;
  const response = await fetch(GET_USER_DEVICES_URL, {
    method: 'GET',
    headers: {

    },
  });

  if (!response.ok) {
    throw new Error('Failed to fetch user devices');
  }

  return response.json();
};

const UserDetails = () => {
  const { id } = useParams();
  const [userDetails, setUserDetails] = useState({});
  const [userDevices, setUserDevices] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        const details = await getUserDetails(id);
        console.log('User Details:', details);
        setUserDetails(details);
  
        const devices = await getUserDevices(id);
        console.log('User Devices:', devices);
        setUserDevices(devices);
  
      } catch (error) {
        setError(error.message);
      }
    };
  
    fetchUserDetails();
  }, [id]);
  

  return (
    <div>
      <h2>User Details</h2>
      {error && <p>Error: {error}</p>}
      {
        userDetails && (
          <div>
            <p>Username: {userDetails.username}</p>
            <p>Email: {userDetails.email}</p>
            <p>Role: {userDetails.roles && userDetails.roles[0].name.split("ROLE_")}</p>
          </div>
        )
      }
    
      {userDevices.length === 0 && <p>No devices found</p>}

      {userDevices && userDevices.length > 0 && (
        <table>
          <thead>
            <tr>
              <th>Description</th>
              <th>Address</th>
              <th>Maximum Hourly Energy Consumption</th>
            </tr>
          </thead>
          <tbody>
            {userDevices.map((device) => (
              <tr key={device.id}>
                <td>{device.description}</td>
                <td>{device.address}</td>
                <td>{device.maximumHourlyEnergyConsumption}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )} 
    </div>
  );
};

export default AuthFilter(UserDetails);
