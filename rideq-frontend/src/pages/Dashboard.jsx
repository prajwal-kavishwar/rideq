import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

import api from "../api/axios";

function Dashboard() {

    const role = localStorage.getItem("role");

    const navigate = useNavigate();

    const [isOnline, setIsOnline] = useState(false);

    useEffect(() => {

        const savedStatus =
            localStorage.getItem("driverOnlineStatus");

        if (savedStatus === "true") {

            setIsOnline(true);
        }

    }, []);

    async function goOnline() {

        try {

            const token = localStorage.getItem("token");

            await api.post(
                "/api/drivers/online",
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            setIsOnline(true);

            localStorage.setItem(
                "driverOnlineStatus",
                "true"
            );

        } catch (error) {

            console.log(error);

            alert("Failed");
        }
    }

    function goOffline() {

        setIsOnline(false);

        localStorage.setItem(
            "driverOnlineStatus",
            "false"
        );
    }

    return (

        <div className="min-h-screen p-10">

            <div className="max-w-6xl mx-auto">

                <h1 className="text-5xl font-bold mb-3">
                    Dashboard
                </h1>

                <p className="text-gray-600 text-lg mb-10">
                    Manage your rides and trips
                </p>

                {role === "USER" && (

                    <div className="grid grid-cols-1 md:grid-cols-2 gap-8">

                        <button
                            onClick={() => navigate("/book-ride")}
                            className="bg-black text-white rounded-3xl p-10 text-left hover:shadow-xl transition h-[260px]"
                        >

                            <h2 className="text-3xl font-bold mb-3">
                                Book Ride
                            </h2>

                            <p className="text-gray-300 text-lg">
                                Create a new ride instantly.
                            </p>

                        </button>

                        <button
                            onClick={() => navigate("/ride-history")}
                            className="bg-white rounded-3xl p-10 text-left shadow-md hover:shadow-xl transition h-[260px]"
                        >

                            <h2 className="text-3xl font-bold mb-3">
                                Ride History
                            </h2>

                            <p className="text-gray-600 text-lg">
                                View previous and active rides.
                            </p>

                        </button>

                    </div>

                )}

                {role === "DRIVER" && (

                    <div className="grid grid-cols-1 md:grid-cols-3 gap-8">

                        <div
                            className="bg-zinc-900 rounded-3xl p-10 text-white shadow-md transition h-[360px] flex flex-col justify-between"
                        >

                            <div>

                                <div className="flex items-center justify-between mb-6">

                                    <h2 className="text-3xl font-bold">

                                        Driver Location

                                    </h2>

                                    <div
                                        className="px-4 py-2 rounded-full text-sm font-semibold bg-white text-black"
                                    >

                                        ACTIVE

                                    </div>

                                </div>

                                <p className="text-lg leading-relaxed">

                                    Select your current location before accepting rides.

                                </p>

                            </div>

                            <div className="flex flex-col gap-4">

                                <button
                                    onClick={() => navigate("/driver-location")}
                                    className="bg-white text-black px-5 py-4 rounded-2xl font-semibold hover:bg-gray-200 transition"
                                >
                                    Choose Current Location
                                </button>

                                <p className="text-gray-300 text-sm">

                                    If no location is selected,
                                    last known location will be used.

                                </p>

                            </div>

                        </div>

                        <button
                            onClick={() => navigate("/available-trips")}
                            className="bg-black text-white rounded-3xl p-10 text-left hover:shadow-xl transition h-[360px]"
                        >

                            <h2 className="text-3xl font-bold mb-3">
                                Available Trips
                            </h2>

                            <p className="text-gray-300 text-lg">
                                View incoming ride requests.
                            </p>

                        </button>

                        <button
                            onClick={() => navigate("/driver-trips")}
                            className="bg-white rounded-3xl p-10 text-left shadow-md hover:shadow-xl transition h-[360px]"
                        >

                            <h2 className="text-3xl font-bold mb-3">
                                My Trips
                            </h2>

                            <p className="text-gray-600 text-lg">
                                Manage accepted trips.
                            </p>

                        </button>

                    </div>

                )}

            </div>

        </div>
    );
}

export default Dashboard;