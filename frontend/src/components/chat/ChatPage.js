import React, { useState, useEffect, useRef } from 'react';
import { List, ListItem, ListItemAvatar, Avatar, ListItemText, TextField, Button } from '@mui/material';
import SendIcon from '@mui/icons-material/Send';
import AttachFileIcon from '@mui/icons-material/AttachFile';
import './ChatPage.css';
import SockJS from 'sockjs-client';
import StompJs from 'stompjs';
import AuthService from "../../services/auth.service";
import Cookies from 'js-cookie';

const getToken = () => {
    return localStorage.getItem('Token');
};

const ChatPage = () => {
    const [users, setUsers] = React.useState([]);
    const [selectedUser, setSelectedUser] = useState(null); // State for the selected user
    const [error, setError] = useState(null);
    const [messages, setMessages] = useState({}); // State for storing messages per user
    const [stompClient, setStompClient] = useState(null);
    const [chat, setChat] = useState([]); // State for the chat messages
    const [msg, setMsg] = useState(""); // State for the message input field

    const isValidJson = (jsonString) => {
        try {
            JSON.parse(jsonString);
            return true;
        } catch (error) {
            return false;
        }
    };

    const messagesEndRef = useRef(null);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    };


    const initWebSocket = () => {
        const socket = new SockJS('http://localhost:8091/ws', {
            headers: {
                Authorization: 'Bearer ' + getToken(),
            },
        });

        const newStompClient = StompJs.over(socket);

        console.log("cookies from websocket: ", Cookies.get('Token'));

        newStompClient.connect({
            withCredentials: true,
            headers: {
            Authorization: 'Bearer ' + getToken()
        }
    }, () => {
            const email = AuthService.getCurrentUser().email; 
            const userSpecificDestination = `/topic/chat/${selectedUser.email}/${email}`;
            console.log(userSpecificDestination);
            
            newStompClient.subscribe(userSpecificDestination, (message) => {
                const formattedMessage = message.body; // Get the message body

                    let receivedMessage;
                    if (isValidJson(formattedMessage)) {
                        // Message is a valid JSON
                        receivedMessage = JSON.parse(formattedMessage);
                    }
                    else{
                        receivedMessage=formattedMessage;
                    }
                    const parts = userSpecificDestination.split('/');
                    const emailSelected = parts[3];
                    console.log("mesage received: ", receivedMessage);
                    console.log("the user of the message sent is: ", emailSelected)
                    if(selectedUser.email === emailSelected) {
                        const formattedMessage = {
                            key: chat.length + 1,
                            color: "red",
                            type: "other",
                            msg: receivedMessage,
                           
                        };
                        // Process the received message and update the chat
                       
                        setChat(prevChat => [...prevChat, formattedMessage]);

                        scrollToBottom();

                        console.log("chat: ", chat);
                    }
            });

            // Other logic related to WebSocket connection
            setStompClient(newStompClient); // Update the stompClient state
        });
    };

    useEffect(() => {
        let isMounted = true; // flag to track mounting status 

        const getUsersList = async () => {
            const USERS_URL = "http://localhost:8081/api/users";
            try {
                const response = await fetch(USERS_URL + "/all", {
                    method: 'GET',
                    headers: {
                        Authorization: 'Bearer ' + getToken(),
                    },
                });
                if (!response.ok) {
                    throw new Error('Failed to fetch users');
                }
                const data = await response.json();
                
                console.log("datas:")
                console.log(data);

                const admins = data.filter(user => user.roles[0].name === "ROLE_ADMIN");
                console.log("admins:")
                console.log(admins);

                console.log("current user:")
                console.log(AuthService.getCurrentUser());
      
                if(AuthService.getCurrentUser().roles[0] === "ROLE_ADMIN"){
                    setUsers(data.filter(user => user.roles[0].name === "ROLE_USER"));
                    console.log("users:")
                    console.log(data.filter(user => user.roles[0].name === "ROLE_USER"));
                }
                else{
                    setUsers(admins);
                    console.log("users from else:")
                    console.log(admins);
                }
                
            } catch (error) {
                if (isMounted) {
                    setError(error.message);
                }
            }
        };

        getUsersList();

        // Cleanup function
        return () => {
            isMounted = false;
            if (stompClient) {
                stompClient.disconnect();
            }
        };
    }, [stompClient]);
    

    const handleUserClick = async (user) => {
        setSelectedUser(user);
        initWebSocket(user);
        if (!messages[user.id]) { // Fetch messages only if not already fetched
            try {
              const userMessages = await getUserMessages(user.email);
               setMessages(prevMessages => ({ ...prevMessages, [user.id]: userMessages }));
               if (selectedUser?.id !== user.id) {
                setChat([]); // Reset chat only when switching users
            }
            } catch (error) {
                console.error('Failed to fetch messages', error);
            }
        }
    };   

    const sendMessage = () => {
        console.log("the user of the chat entered is: ", selectedUser)
        if (stompClient && msg !== "") {
            console.log("sending...")
            const chatMessage = {
                senderEmail: AuthService.getCurrentUser().email,
                key: chat.length + 1,
                type: "",
                msg: msg,
                color: "blue",
            };
            // Send the message through WebSocket
            //stompClient.send(`/app/chat/${sessionStorage.getItem('email')}/${this.props.emailSelected}`, {}, msg);

            stompClient.send(`/app/chat/${AuthService.getCurrentUser().email}/${selectedUser.email}`, {},JSON.stringify(msg));
            console.log("sent message to the user: ", selectedUser.email, " from the user: ", AuthService.getCurrentUser().email)
            console.log("message sent: ", msg);
            // Update the chat with the sent message

            setChat(prevChat => [...prevChat, chatMessage]);

            setMsg("");

            scrollToBottom();
        }
    };


    const getUserMessages = async (userEmail) => {
        console.log("getting messages from user: ", userEmail)
        const MESSAGES_URL = "http://localhost:8091/api/messages?receiver=";
        try {
            const response1 = await fetch(MESSAGES_URL + AuthService.getCurrentUser().email + "&sender=" + userEmail, {
                method: 'GET',
                credentials: 'include',
                headers: {
                    Authorization: 'Bearer ' + getToken(),
                },
            });
            if (!response1.ok) {
                throw new Error('Failed to fetch messages');
            }
    
            const data = await response1.json();

            const response2 = await fetch(MESSAGES_URL + userEmail + "&sender=" + AuthService.getCurrentUser().email, {
                method: 'GET',
                credentials: 'include',
                headers: {
                    Authorization: 'Bearer ' + getToken(),
                },
            });

            if (!response2.ok) {
                throw new Error('Failed to fetch messages');
            }

            const data2 = await response2.json();

            data.push(...data2);

            // Assuming the response data is an array of MessageDTO objects
            const messages = data.map((message) => ({
                key: message.id, // You may need to adjust this based on your message structure
                color: message.sender === currentUserEmail ? 'blue' : 'red',
                type: 'text', // You can adjust the type as needed
                msg: message.content, // Use the content from MessageDTO
                senderEmail: message.sender,
                receiverEmail: message.receiver,
                time: new Date(message.time), // Convert the timestamp to a Date object
            }));
    
            // Sort the messages by their timestamp (date)
            messages.sort((a, b) => a.time - b.time);
    
            // Set the chat state with the sorted messages
            setChat(messages);
    
            console.log("messages:");
            console.log(messages);
        } catch (error) {
            console.error('Failed to fetch messages', error);
        }
    };
    

    if (error) {
        return <div>Error: {error}</div>;
    }

    const currentUserEmail = AuthService.getCurrentUser().email;

    return (
        <div className="chat-container">
            <div className="user-list">
                <List>
                    { users.map((user) => (
                        <ListItem button key={user.id} onClick={() => handleUserClick(user)}>
                            <ListItemAvatar>
                                <Avatar alt={user.username} src="/path-to-user-image.jpg" />
                            </ListItemAvatar>
                            <ListItemText primary={user.email} />
                        </ListItem>
                    ))}
                </List>
            </div>
            <div className="chat-content">
                <div className="chat-header">{selectedUser ? selectedUser.email : 'Select a user'}</div>
                <div className="messages">
                    {chat.map((message, index) => (
                        <div
                            key={index}
                            className={`message ${
                                message.senderEmail === currentUserEmail ? 'message-right' : 'message-left'
                            }`}
                        >
                            <div className="message-content">{message.msg.replaceAll('\"', "")}</div>
                        </div>
                    ))}
                    <div ref={messagesEndRef} /> {/* Invisible element at the end of your messages */}
                </div>
                <div className="message-input">
                    <TextField fullWidth placeholder="Write a message..." onChange={(e) => setMsg(e.target.value)} value={msg} />
                    <Button variant="contained" color="primary" endIcon={<SendIcon />} onClick={sendMessage}>
                        Send
                    </Button>
                    <Button variant="contained" color="secondary" startIcon={<AttachFileIcon />}>
                        Attach
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default ChatPage;
