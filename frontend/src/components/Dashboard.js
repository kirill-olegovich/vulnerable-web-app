// Purpose:
// Shows content only accessible after login. It might display a welcome message,
// fetch user‑specific data (using the authenticated api instance), and include a logout button.

import React from 'react';
import { useNavigate } from 'react-router-dom';
import authService from '../services/auth.service';
import api from '../api';

const Dashboard = () => {
  const navigate = useNavigate();
  const user = authService.getCurrentUser();

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  // Example of calling a protected endpoint
  const fetchUserData = async () => {
    try {
      const response = await api.get('/user/profile'); // assumes backend endpoint
      console.log(response.data);
    } catch (error) {
      console.error('Failed to fetch profile');
    }
  };

  return (
    <div>
      <h1>Welcome, {user?.username}!</h1>
      <button onClick={fetchUserData}>Load Profile</button>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
};

export default Dashboard;