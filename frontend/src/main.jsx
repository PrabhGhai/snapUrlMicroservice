import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { BrowserRouter} from "react-router";
import { AuthProvider } from './store/AuthContext.jsx';
import { UrlProvider } from './store/UrlServiceContext.jsx';

createRoot(document.getElementById('root')).render(
    <BrowserRouter>
        <AuthProvider>
            <UrlProvider>
                <App />
            </UrlProvider>
        </AuthProvider>
   </BrowserRouter>
    
)
