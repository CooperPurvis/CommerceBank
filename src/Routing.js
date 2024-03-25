import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Login from './Login'; // Import your Login component

const Routing = () => {
    return (
        <Router>
            <div>
                {/* Route for the Login page */}
                <Route path="/login" component={Login} />
                {/* Add more routes for other pages as needed */}
            </div>
        </Router>
    );
};

export default Routing;
