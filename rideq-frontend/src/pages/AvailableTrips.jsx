import { useEffect, useState } from "react";
import api from "../api/axios";

function AvailableTrips() {

    const [trips, setTrips] = useState([]);

    async function fetchTrips() {

        try {

            const token = localStorage.getItem("token");

            const response = await api.get(
                "/api/trips/available",
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

    useEffect(() => {
        fetchTrips();
    }, []);

    async function acceptTrip(tripId) {

        try {

            const token =
                localStorage.getItem("token");

            await api.post(
                `/api/drivers/trip/${tripId}/accept`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            await fetchTrips();

            alert("Trip accepted");

        } catch (error) {

            console.log(error);

            alert("Accept failed");
        }
    }

    async function rejectTrip(tripId) {

        try {

            const token =
                localStorage.getItem("token");

            await api.post(
                `/api/drivers/trip/${tripId}/reject`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            setTrips(
                trips.filter(
                    (trip) => trip.tripId !== tripId
                )
            );

            alert("Trip rejected");

        } catch (error) {

            console.log(error);

            alert("Reject failed");
        }
    }



    return (

        <div className="p-10">

            <h1 className="text-3xl font-bold mb-6">
                Available Trips
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

                            <button
                                onClick={() => acceptTrip(trip.id)}
                                className="bg-green-600 text-white px-6 py-3 rounded-xl"
                            >
                                Accept Trip
                            </button>

                            <button
                                onClick={() => rejectTrip(trip.id)}
                                className="bg-red-500 text-white px-6 py-3 rounded-xl"
                            >
                                Reject Trip
                            </button>

                        </div>

                    </div>

                ))}

            </div>

        </div>
    );
}

export default AvailableTrips;