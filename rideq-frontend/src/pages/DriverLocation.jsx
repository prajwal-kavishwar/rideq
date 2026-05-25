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

        <div className="p-10">

            <h1 className="text-3xl font-bold mb-6">
                Select Driver Location
            </h1>

            <MapView
                pickupLocation={pickupLocation}
                setPickupLocation={setPickupLocation}
                dropLocation={null}
                setDropLocation={() => {}}
            />

            {pickupLocation && (

                <button
                    onClick={saveLocation}
                    className="mt-6 bg-black text-white px-6 py-3 rounded-xl"
                >
                    Save Location
                </button>

            )}

        </div>
    );
}

export default DriverLocation;