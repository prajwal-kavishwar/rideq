import { useEffect, useState } from "react";
import api from "../api/axios";

function DriverTrips() {

    const [trips, setTrips] = useState([]);

    async function fetchTrips() {

        try {

            const token = localStorage.getItem("token");

            const response = await api.get(
                "/api/trips/driver",
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            const filteredTrips = response.data.content.filter(
                trip =>
                    trip.status === "ACCEPTED" ||
                    trip.status === "STARTED" ||
                    trip.status === "COMPLETED"
            );

            setTrips(filteredTrips);

        } catch (error) {

            console.log(error);
        }
    }

    useEffect(() => {
        fetchTrips();
    }, []);

    async function startTrip(tripId) {

        try {

            const token = localStorage.getItem("token");

            await api.post(
                `/api/trips/${tripId}/start`,
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

    async function completeTrip(tripId) {

        try {

            const token = localStorage.getItem("token");

            await api.post(
                `/api/trips/${tripId}/complete`,
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

    return (

        <div className="p-10">

            <h1 className="text-3xl font-bold mb-6">
                Driver Trips
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

                        <div className="flex gap-4 mt-4">

                            {trip.status === "ACCEPTED" && (

                                <button
                                    onClick={() => startTrip(trip.id)}
                                    className="bg-blue-600 text-white px-6 py-3 rounded-xl"
                                >
                                    Start Trip
                                </button>

                            )}

                            {trip.status === "STARTED" && (

                                <button
                                    onClick={() => completeTrip(trip.id)}
                                    className="bg-green-600 text-white px-6 py-3 rounded-xl"
                                >
                                    Complete Trip
                                </button>

                            )}

                            {
                                trip.status !== "COMPLETED"
                                &&
                                (
                                    <button
                                        onClick={() => cancelTrip(trip.id)}
                                        className="bg-red-500 text-white px-6 py-3 rounded-xl"
                                    >
                                        Cancel Trip
                                    </button>
                                )
                            }

                        </div>

                    </div>

                ))}

            </div>

        </div>
    );
}

export default DriverTrips;