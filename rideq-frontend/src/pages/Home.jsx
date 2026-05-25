import { Link } from "react-router-dom";
import { useEffect, useState } from "react";

function Home() {

    const [token, setToken] = useState(
        localStorage.getItem("token")
    );

    const [role, setRole] = useState(
        localStorage.getItem("role")
    );

    useEffect(() => {

        setToken(localStorage.getItem("token"));

        setRole(localStorage.getItem("role"));

    }, []);

    return (

        <div className="min-h-screen flex items-center justify-center px-6">

            <div className="max-w-6xl w-full grid grid-cols-1 lg:grid-cols-2 gap-16 items-center">

                <div>

                    <div className="inline-block bg-black text-white px-5 py-2 rounded-full mb-6 text-sm font-semibold">

                        Ride Booking Platform

                    </div>

                    <h1 className="text-7xl font-bold leading-tight mb-8">

                        Go anywhere with RideQ

                    </h1>

                    <p className="text-gray-600 text-xl leading-relaxed mb-8">

                        Book rides, manage trips, and travel smarter with a modern ride-booking experience.

                    </p>

                    {token && role && (

                        <p className="text-3xl font-bold mb-8">

                            Welcome {role}

                        </p>

                    )}

                    {!token && (

                        <div className="flex gap-5">

                            <Link to="/login">

                                <button className="bg-black text-white px-8 py-4 rounded-2xl text-lg font-semibold hover:bg-zinc-800 transition">

                                    Login

                                </button>

                            </Link>

                            <Link to="/register">

                                <button className="bg-white border border-gray-300 px-8 py-4 rounded-2xl text-lg font-semibold hover:bg-gray-100 transition">

                                    Register

                                </button>

                            </Link>

                        </div>

                    )}

                    {token && (

                        <Link to="/dashboard">

                            <button className="bg-black text-white px-8 py-4 rounded-2xl text-lg font-semibold hover:bg-zinc-800 transition">

                                Open Dashboard

                            </button>

                        </Link>

                    )}

                </div>

                <div className="bg-black rounded-[40px] p-12 text-white">

                    <div className="flex flex-col gap-10">

                        <div>

                            <h2 className="text-3xl font-bold mb-3">
                                Book Trips
                            </h2>

                            <p className="text-gray-300 text-lg">
                                Riders can instantly create and manage trips.
                            </p>

                        </div>

                        <div>

                            <h2 className="text-3xl font-bold mb-3">
                                Live Maps
                            </h2>

                            <p className="text-gray-300 text-lg">
                                Select pickup and drop locations interactively.
                            </p>

                        </div>

                        <div>

                            <h2 className="text-3xl font-bold mb-3">
                                Driver Controls
                            </h2>

                            <p className="text-gray-300 text-lg">
                                Drivers can accept, start, complete, and manage rides.
                            </p>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}

export default Home;