import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import './DeviceEdit.css';
import AuthFilter from '../../services/AuthFilter';

const getDeviceDetails = async (deviceId) => {
  const DEVICE_DETAILS_URL = `http://localhost:8085/api/devices/${deviceId}`;
  const response = await fetch(DEVICE_DETAILS_URL, {
    method: 'GET',
    headers: {
    },
  });

  if (!response.ok) {
    throw new Error('Failed to fetch device details');
  }

  return response.json();
};

const updateDeviceDetails = async (deviceId, updatedDetails) => {
  const UPDATE_DEVICE_URL = `http://localhost:8085/api/devices/${deviceId}/update`;
  const response = await fetch(UPDATE_DEVICE_URL, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(updatedDetails),
  });

  if (!response.ok) {
    throw new Error('Failed to update device details');
  }

  return response.json();
};

const DeviceEdit = () => {
    const navigate = useNavigate();
  const { id } = useParams();
  const [deviceDetails, setDeviceDetails] = useState(null);
  const [updatedDetails, setUpdatedDetails] = useState({});
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchDeviceDetails = async () => {
      try {
        const details = await getDeviceDetails(id);
        setDeviceDetails(details);
        setUpdatedDetails(details); // Initialize updatedDetails with current details
      } catch (error) {
        setError(error.message);
      }
    };

    fetchDeviceDetails();
  }, [id]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUpdatedDetails((prevDetails) => ({ ...prevDetails, [name]: value }));
  };

  const handleUpdate = async () => {
    try {
      await updateDeviceDetails(id, updatedDetails);
      navigate('/admin/devices');
      console.log('Device details updated successfully');
    } catch (error) {
      setError(error.message);
    }
  };

  return (
    <div>
      <h2>Edit Device Details</h2>
      {error && <p>Error: {error}</p>}
      {deviceDetails && (
        <div>
          <label>Description:</label>
          <input
            type="text"
            name="description"
            value={updatedDetails.description || ''}
            onChange={handleInputChange}
          />
            <label>Address:</label>
            <input
                type="text"
                name="address"
                value={updatedDetails.address || ''}
                onChange={handleInputChange}
            />
            <label>Maximum Hourly Energy Consumption:</label>
            <input
                type="text"
                name="maximumHourlyEnergyConsumption"
                value={updatedDetails.maximumHourlyEnergyConsumption || ''}
                onChange={handleInputChange}
            />
            <label>User ID:</label>
            <input
                type="text"
                name="userId"
                value={updatedDetails.userId || ''}
                onChange={handleInputChange}
            />
          <button onClick={handleUpdate}>Update</button>
        </div>
      )}
    </div>
  );
};

export default AuthFilter(DeviceEdit);
