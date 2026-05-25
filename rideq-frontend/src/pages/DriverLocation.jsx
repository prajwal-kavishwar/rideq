import { useState } from "react";
import api from "../api/axios";
import MapView from "../components/MapView";

function DriverLocation() {

    const [pickupLocation, setPickupLocation] = useState(null);

    async function saveLocation() {

        try {

            const token = localStorage.getItem("token");

            await api.post(
                "/api/drivers/location",
                pickupLocation,
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            alert("Driver location updated");

        } catch (error) {

            console.log(error);

            alert("Failed to update location");
        }
    }

    return (

        <div className="min-h-screen p-10">

            <div className="max-w-7xl mx-auto">

                <h1 className="text-5xl font-bold mb-3">
                    Driver Location
                </h1>

                <p className="text-gray-600 text-lg mb-10">
                    Select current driver location on map
                </p>

                <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">

                    <div className="lg:col-span-2 bg-white rounded-3xl shadow-md overflow-hidden">

                        <MapView
                            pickupLocation={pickupLocation}
                            setPickupLocation={setPickupLocation}
                            dropLocation={null}
                            setDropLocation={() => {}}
                        />

                    </div>

                    <div className="bg-white rounded-3xl shadow-md p-8 h-fit">

                        <h2 className="text-3xl font-bold mb-8">
                            Current Location
                        </h2>

                        <div className="bg-gray-100 rounded-2xl p-5">

                            <p className="text-sm text-gray-500 mb-2">
                                DRIVER LOCATION
                            </p>

                            <p className="font-semibold break-all">

                                {
                                    pickupLocation
                                        ? `${pickupLocation.latitude}, ${pickupLocation.longitude}`
                                        : "Select driver location on map"
                                }

                            </p>

                        </div>

                        {pickupLocation && (

                            <button
                                onClick={saveLocation}
                                className="w-full mt-8 bg-black text-white py-4 rounded-2xl text-lg font-semibold hover:bg-zinc-800 transition"
                            >
                                Save Location
                            </button>

                        )}

                    </div>

                </div>

            </div>

        </div>
    );
}

export default DriverLocation;