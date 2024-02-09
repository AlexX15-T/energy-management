import React, { useState, useEffect } from 'react';
import './DeviceDetails.css';
import { useParams } from 'react-router-dom';
import AuthFilter from '../../services/AuthFilter';

const getDeviceDetails = async (deviceId) => {
  const DEVICE_DETAILS_URL = `http://localhost:8085/api/devices/${deviceId}`;
  const response = await fetch(DEVICE_DETAILS_URL, {
    method: 'GET',
    headers: {
    }
  });

  if (!response.ok) {
    throw new Error('Failed to fetch device details');
  }

  return response.json();
};

const DeviceDetails = ({ match }) => {
    const{ id } = useParams();
  const [deviceDetails, setDeviceDetails] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchDeviceDetails = async () => {
      const deviceId = id;

      try {
        const details = await getDeviceDetails(deviceId);
        setDeviceDetails(details);
      } catch (error) {
        setError(error.message);
      }
    };

    fetchDeviceDetails();
  }, [id]);

  return (
    <div>
      <h2>Device Details</h2>
      {error && <p>Error: {error}</p>}
      {deviceDetails && (
        <div>
          <p>Description: {deviceDetails.description}</p>
          <p>Address: {deviceDetails.address}</p>
          <p>Maximum Hourly Energy Consumption: {deviceDetails.maximumHourlyEnergyConsumption}</p>
          <p>User ID: {deviceDetails.userId}</p>
        </div>
      )}
    </div>
  );
};

export default AuthFilter(DeviceDetails);
