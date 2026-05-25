import { BrowserRouter, Routes, Route } from "react-router-dom";


import DriverTrips from "./pages/DriverTrips";
import DriverLocation from "./pages/DriverLocation";
import AvailableTrips from "./pages/AvailableTrips";
import RideHistory from "./pages/RideHistory";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import ProtectedRoute from "./components/ProtectedRoute";
import Navbar from "./components/Navbar";
import BookRide from "./pages/BookRide";

function App() {
    return (
        <BrowserRouter>
            <Navbar />
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />

                <Route
                    path="/dashboard"
                    element={
                        <ProtectedRoute>
                            <Dashboard />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/book-ride"
                    element={
                        <ProtectedRoute>
                            <BookRide />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/ride-history"
                    element={
                        <ProtectedRoute>
                            <RideHistory />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/available-trips"
                    element={
                        <ProtectedRoute>
                            <AvailableTrips />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/driver-location"
                    element={
                        <ProtectedRoute>
                            <DriverLocation />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/driver-trips"
                    element={
                        <ProtectedRoute>
                            <DriverTrips />
                        </ProtectedRoute>
                    }
                />
            </Routes>
        </BrowserRouter>
    );
}

export default App;