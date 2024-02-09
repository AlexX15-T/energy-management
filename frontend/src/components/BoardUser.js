import React, { useState, useEffect } from "react";
import UserService from "../services/user.service";
import EventBus from "../common/EventBus";
import ExistingUserFilter from '../services/ExistingUserFilter';
import SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import Alert from '@mui/material/Alert';
import AlertTitle from '@mui/material/AlertTitle';


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

const BoardUser = () => {
  const [content, setContent] = useState("");
  const [userDevices, setUserDevices] = useState([]);
  const [user, setUser] = useState(null);
  const [error, setError] = useState(null);
  const [stompClient, setStompClient] = useState(null);
  const [alertVisible, setAlertVisible] = useState(false);
  const [notification, setNotification] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await UserService.getUserBoard();
        response.text().then((text) => {
          console.log('BoardUser response text:', text);
          setContent('Welcome back');
        });
        const user = JSON.parse(localStorage.getItem('user'));
        setUser(user);
        const devices = await getUserDevices(user.id); // Use await here
        console.log('User Devices:', devices);
        setUserDevices(devices);
      } catch (error) {
        const _content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        setContent(_content);

        if (error.response && error.response.status === 401) {
          EventBus.dispatch("logout");
        }
      }
    };

    fetchData();


    const socket = new SockJS('http://localhost:8083/ws');
    const stompClient = Stomp.over(socket);
    setStompClient(stompClient);

    console.log('Connecting to websocket...');

    stompClient.connect({}, (frame) => {
      const currentUserId = JSON.parse(localStorage.getItem('user')).id;
      console.log('Connected: ' + frame);
      console.log('Subscribing to user ' + currentUserId + ' notifications...');

      const subscribeLink = `/topic/user/${currentUserId}/notification`;

      stompClient.subscribe(subscribeLink, (notification) => {
        console.log('Received notification:', notification);
        setNotification(notification.body);
        setAlertVisible(true);

        setTimeout(() => {
          setAlertVisible(false);
          setNotification(null);
        }, 5000);
      });
    });

    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    }
  }, []);

  return (
    <div className="container">
      <header className="jumbotron">
        <h3>{content}</h3>
        <p>Here is a list of all your devices:</p>
      </header>
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
                <td>{device.description || ''}</td>
                <td>{device.address || ''}</td>
                <td>{device.maximumHourlyEnergyConsumption}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {alertVisible && notification && (
                <Alert severity="warning" onClose={() => setAlertVisible(false)}  style={{
                    opacity: alertVisible ? '1' : '0',
                    transition: 'opacity 0.5s ease-in-out',
                }}>
                    <AlertTitle>Warning</AlertTitle>
                    {notification}
                </Alert>
            )}

    </div>
  );
};

export default ExistingUserFilter(BoardUser);
