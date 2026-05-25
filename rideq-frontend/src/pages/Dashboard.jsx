import { useNavigate } from "react-router-dom";
import api from "../api/axios";

function Dashboard() {

    const role = localStorage.getItem("role");

    const navigate = useNavigate();

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

            alert("Driver online");

            navigate("/driver-location");

        } catch (error) {

            console.log(error);

            alert("Failed");
        }
    }

    return (

        <div className="p-10">

            <h1 className="text-4xl font-bold mb-8">
                Dashboard
            </h1>

            {role === "USER" && (

                <div className="flex gap-4">

                    <button
                        onClick={() => navigate("/book-ride")}
                        className="bg-black text-white px-6 py-3 rounded-xl"
                    >
                        Book Ride
                    </button>

                    <button
                        onClick={() => navigate("/ride-history")}
                        className="bg-blue-600 text-white px-6 py-3 rounded-xl"
                    >
                        Ride History
                    </button>

                </div>

            )}

            {role === "DRIVER" && (

                <div className="flex gap-4">

                    <button
                        onClick={goOnline}
                        className="bg-green-600 text-white px-6 py-3 rounded-xl"
                    >
                        Go Online
                    </button>

                    <button
                        onClick={() => navigate("/available-trips")}
                        className="bg-black text-white px-6 py-3 rounded-xl"
                    >
                        Available Trips
                    </button>

                    <button
                        onClick={() => navigate("/driver-trips")}
                        className="bg-blue-600 text-white px-6 py-3 rounded-xl"
                    >
                        My Trips
                    </button>

                </div>

            )}

        </div>
    );
}

export default Dashboard;