import { Link, useNavigate, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";

function Navbar() {

    const navigate = useNavigate();

    const location = useLocation();

    const [token, setToken] = useState(
        localStorage.getItem("token")
    );

    const [role, setRole] = useState(
        localStorage.getItem("role")
    );

    useEffect(() => {

        setToken(localStorage.getItem("token"));

        setRole(localStorage.getItem("role"));

    }, [location]);

    function handleLogout() {

        localStorage.removeItem("token");

        localStorage.removeItem("role");

        localStorage.removeItem("driverOnlineStatus");

        window.location.href = "/";
    }

    const hideNavigation =
        location.pathname === "/" ||
        location.pathname === "/login" ||
        location.pathname === "/register";

    return (

        <div className="bg-black text-white px-8 py-5 flex items-center justify-between shadow-md">

            <div className="flex items-center gap-5">

                <Link
                    to="/"
                    className="text-3xl font-bold tracking-tight"
                >
                    RideQ
                </Link>

                {location.pathname !== "/" && (

                    <button
                        onClick={() => navigate(-1)}
                        className="bg-zinc-800 hover:bg-zinc-700 px-4 py-2 rounded-2xl transition"
                    >
                        Back
                    </button>

                )}

                {!hideNavigation && (

                    <>
                        <Link
                            to="/"
                            className="hover:text-gray-300 transition"
                        >
                            Home
                        </Link>

                        {token && (

                            <Link
                                to="/dashboard"
                                className="hover:text-gray-300 transition"
                            >
                                Dashboard
                            </Link>

                        )}

                    </>

                )}

            </div>

            <div className="flex items-center gap-4">

                {token && role && (

                    <div className="bg-zinc-800 px-5 py-2 rounded-2xl font-semibold">

                        {role}

                    </div>

                )}

                {token && (

                    <button
                        onClick={handleLogout}
                        className="bg-white text-black px-5 py-2 rounded-2xl font-semibold hover:bg-gray-200 transition"
                    >
                        Logout
                    </button>

                )}

            </div>

        </div>
    );
}

export default Navbar;