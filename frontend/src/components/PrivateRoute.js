// Purpose:
// Protects routes that require authentication. If the user is not logged in (no token in localStorage),
// it redirects to /login. Otherwise, it renders the child component (the protected page).

import { Navigate } from 'react-router-dom';
import authService from '../services/auth.service';

const PrivateRoute = ({ children }) => {
  const user = authService.getCurrentUser();
  return user ? children : <Navigate to="/login" />;
};

export default PrivateRoute;