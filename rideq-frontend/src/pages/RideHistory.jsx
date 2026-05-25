import { useEffect, useState } from "react";
import api from "../api/axios";

function RideHistory() {

    const [trips, setTrips] = useState([]);

    async function fetchTrips() {

        try {

            const token = localStorage.getItem("token");

            const response = await api.get(
                "/api/trips/user",
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            setTrips(response.data.content);

        } catch (error) {

            console.log(error);
        }
    }

    async function cancelTrip(tripId) {

        try {

            const token = localStorage.getItem("token");

            await api.post(
                `/api/trips/${tripId}/cancel`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            fetchTrips();

        } catch (error) {

            console.log(error);
        }
    }

    useEffect(() => {
        fetchTrips();
    }, []);

    return (

        <div className="p-10">

            <h1 className="text-3xl font-bold mb-6">
                Ride History
            </h1>

            <div className="flex flex-col gap-4">

                {trips.map((trip) => (

                    <div
                        key={trip.id}
                        className="bg-white shadow-md rounded-xl p-6"
                    >

                        <p>Status: {trip.status}</p>

                        <p>Fare: ₹{trip.fare}</p>

                        <p>Distance: {trip.distance} km</p>

                        {
                            trip.status !== "COMPLETED"
                            &&
                            trip.status !== "CANCELED"
                            &&
                            (
                                <button
                                    onClick={() => cancelTrip(trip.id)}
                                    className="mt-4 bg-red-500 text-white px-6 py-3 rounded-xl"
                                >
                                    Cancel Trip
                                </button>
                            )
                        }

                    </div>

                ))}

            </div>

        </div>
    );
}

export default RideHistory;