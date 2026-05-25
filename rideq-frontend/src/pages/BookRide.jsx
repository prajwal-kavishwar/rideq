import { useState } from "react";

import api from "../api/axios";

import MapView from "../components/MapView";

function BookRide() {

    const [pickupLocation, setPickupLocation] =
        useState(null);

    const [dropLocation, setDropLocation] =
        useState(null);

    const [rideBooked, setRideBooked] =
        useState(false);

    async function handleBookRide() {

        try {

            const token =
                localStorage.getItem("token");

            await api.post(
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

            setRideBooked(true);

            alert("Ride booked successfully");

        } catch (error) {

            console.log(error);

            alert("Ride booking failed");
        }
    }

    function resetLocations() {

        setPickupLocation(null);

        setDropLocation(null);
        setRideBooked(false);
    }

    return (

        <div className="min-h-screen p-10">

            <div className="max-w-7xl mx-auto">

                <h1 className="text-5xl font-bold mb-3">
                    Book Ride
                </h1>

                <p className="text-gray-600 text-lg mb-10">
                    Select pickup and drop locations
                </p>

                <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">

                    <div className="lg:col-span-2 bg-white rounded-3xl shadow-md overflow-hidden">

                        <MapView
                            pickupLocation={pickupLocation}
                            setPickupLocation={setPickupLocation}
                            dropLocation={dropLocation}
                            setDropLocation={setDropLocation}
                        />

                    </div>

                    <div className="bg-white rounded-3xl shadow-md p-8 h-fit">

                        <h2 className="text-3xl font-bold mb-8">
                            Trip Details
                        </h2>

                        <div className="flex flex-col gap-6">

                            <div className="bg-gray-100 rounded-2xl p-5">

                                <p className="text-sm text-gray-500 mb-2">
                                    PICKUP LOCATION
                                </p>

                                <p className="font-semibold break-all">

                                    {
                                        pickupLocation
                                            ? `${pickupLocation.latitude}, ${pickupLocation.longitude}`
                                            : "Select pickup location on map"
                                    }

                                </p>

                            </div>

                            <div className="bg-gray-100 rounded-2xl p-5">

                                <p className="text-sm text-gray-500 mb-2">
                                    DROP LOCATION
                                </p>

                                <p className="font-semibold break-all">

                                    {
                                        dropLocation
                                            ? `${dropLocation.latitude}, ${dropLocation.longitude}`
                                            : "Select drop location on map"
                                    }

                                </p>

                            </div>

                            {rideBooked && (

                                <div className="bg-yellow-100 rounded-2xl p-5">

                                    <p className="text-yellow-700 font-bold text-lg">

                                        Ride booked successfully. Waiting for driver response.

                                    </p>

                                </div>

                            )}

                        </div>

                        <div className="flex flex-col gap-4 mt-8">

                            {!rideBooked && (

                                <button
                                    onClick={handleBookRide}
                                    className="bg-black text-white py-4 rounded-2xl text-lg font-semibold hover:bg-zinc-800 transition"
                                >
                                    Book Ride
                                </button>

                            )}

                            <button
                                onClick={resetLocations}
                                className="bg-gray-200 text-black py-4 rounded-2xl text-lg font-semibold hover:bg-gray-300 transition"
                            >
                                Reset Locations
                            </button>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}

export default BookRide;