import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import './DevicesHome.css'; 
import AuthFilter from '../../services/AuthFilter';

const getToken = () => {
  return localStorage.getItem('Token');
};

const getDevicesList = async () => {
  const DEVICES_URL = "http://localhost:8085/api/devices/";
  const response = await fetch(DEVICES_URL + "all", {
    method: 'GET',
    headers: {
      Authorization: 'Bearer ' + getToken(),
    },
  });

  if (!response.ok) {
    throw new Error('Failed to fetch devices');
  }

  return response.json();
};

const handleDelete = async (deviceId) => {
  console.log(`Delete device with ID ${deviceId}`);
  const DELETE_DEVICE_URL = `http://localhost:8085/api/devices/${deviceId}/delete`;

  try {
    const response = await fetch(DELETE_DEVICE_URL, {
      method: 'DELETE',
      headers: {},
    });

    if (!response.ok) {
      throw new Error('Failed to delete device');
    }

    window.location.reload();
  } catch (error) {
    console.error('Error deleting device:', error.message);
  }
};


const DevicesHome = () => {
  const [devices, setDevices] = useState([]);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleAddDevice = () => {
    navigate('/admin/devices/add');
    console.log(`Add device`);
  }
  

  useEffect(() => {
    const fetchDevices = async () => {
      try {
        const deviceList = await getDevicesList();
        setDevices(deviceList);
      } catch (error) {
        setError(error.message);
      }
    };

    fetchDevices();
  }, []);

  return (
    <div className="devices-container"> {/* Added a class for styling */}
      <h1>Devices List</h1>
      {error && <p className="error-message">Error: {error}</p>} {/* Added a class for styling */}
      <button className="add-device-button" onClick={handleAddDevice}>Add Device</button>
      <table>
        <thead>
          <tr>
            <th>Description</th>
            <th>Address</th>
            <th>Maximum Hourly Energy Consumption</th>
            <th>User ID</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {devices.map(device => (
            <tr key={device.id}>
              <td>{device.description}</td>
              <td>{device.address}</td>
              <td>{device.maximumHourlyEnergyConsumption}</td>
              <td>{device.userId}</td>
              <td>
              <Link to={`${device.id}/details`}>Show Details</Link>
              <button className="delete-button" onClick={() => handleDelete(device.id)}>Delete</button> {/* Added a class for styling */}
              <Link to={`${device.id}/edit`}>Edit</Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AuthFilter(DevicesHome);
