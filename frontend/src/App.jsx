import {Routes, Route } from "react-router";
import Index from "./pages/Index";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Dashboard from "./pages/Dashboard";
import NotFound from "./pages/NotFound.jsx";
import{ Toaster } from 'react-hot-toast';
import { useAuth } from "./store/AuthContext.jsx";
const App = () => {
  const {isLoggedIn} = useAuth();
  return (
    <>
     <Toaster />
      <Routes>
        <Route path="/" element={<Index />} />
         
          {isLoggedIn ?<Route path="/dashboard" element={<Dashboard />} /> :
          <>
           <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          </> }
          
          <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  )
}

export default App