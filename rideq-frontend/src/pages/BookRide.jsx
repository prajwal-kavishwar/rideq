import { useState } from "react";
import api from "../api/axios";
import MapView from "../components/MapView";

function BookRide() {

    const [pickupLocation, setPickupLocation] = useState(null);

    const [dropLocation, setDropLocation] = useState(null);

    async function handleBookRide() {

        try {

            const token = localStorage.getItem("token");

            const response = await api.post(
                "/api/trips",
                {
                    pickupLocation,
                    dropLocation
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            console.log(response.data);

            alert("Ride booked successfully");

        } catch (error) {

            console.log(error);

            alert("Ride booking failed");
        }
    }

    return (

        <div className="p-10">

            <h1 className="text-3xl font-bold mb-6">
                Select Pickup & Drop Location
            </h1>

            <MapView
                pickupLocation={pickupLocation}
                setPickupLocation={setPickupLocation}
                dropLocation={dropLocation}
                setDropLocation={setDropLocation}
            />
            <button
                onClick={() => {
                    setPickupLocation(null);
                    setDropLocation(null);
                }}
                className="mt-4 bg-red-500 text-white px-6 py-3 rounded-xl"
            >
                Reset Locations
            </button>

            <div className="mt-6">

                {pickupLocation && (
                    <p>
                        Pickup Selected
                    </p>
                )}

                {dropLocation && (
                    <p>
                        Drop Selected
                    </p>
                )}

                {pickupLocation && dropLocation && (

                    <button
                        onClick={handleBookRide}
                        className="mt-4 bg-black text-white px-6 py-3 rounded-xl"
                    >
                        Book Ride
                    </button>

                )}

            </div>

        </div>
    );
}

export default BookRide;