import { useState } from "react";

import {
    MapContainer,
    TileLayer,
    Marker,
    useMapEvents
} from "react-leaflet";

function LocationSelector({
                              pickupLocation,
                              setPickupLocation,
                              dropLocation,
                              setDropLocation
                          }) {

    useMapEvents({

        click(e) {

            const clickedLocation = {
                latitude: e.latlng.lat,
                longitude: e.latlng.lng
            };

            if (!pickupLocation) {

                setPickupLocation(clickedLocation);

            } else if (!dropLocation) {

                setDropLocation(clickedLocation);
            }
        },
    });

    return null;
}

function MapView({
                     pickupLocation,
                     setPickupLocation,
                     dropLocation,
                     setDropLocation
                 }) {

    const [loadingLocation, setLoadingLocation] =
        useState(false);

    function chooseCurrentLocation() {

        if (!navigator.geolocation) {

            alert("Geolocation not supported");

            return;
        }

        setLoadingLocation(true);

        navigator.geolocation.getCurrentPosition(

            (position) => {

                const currentLocation = {
                    latitude: position.coords.latitude,
                    longitude: position.coords.longitude
                };

                setPickupLocation(currentLocation);

                setLoadingLocation(false);
            },

            () => {

                alert("Location permission denied");

                setLoadingLocation(false);
            },

            {
                enableHighAccuracy: true,
                timeout: 10000
            }
        );
    }

    return (

        <div className="relative">

            <button
                onClick={chooseCurrentLocation}
                disabled={loadingLocation}
                className="absolute z-[1000] top-4 right-4 bg-black text-white px-5 py-3 rounded-2xl shadow-lg min-w-[220px]"
            >

                {
                    loadingLocation
                        ? "Getting Location..."
                        : "Use Current Location"
                }

            </button>

            <MapContainer
                center={[23.2599, 77.4126]}
                zoom={13}
                style={{
                    height: "600px",
                    width: "100%"
                }}
            >

                <TileLayer
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />

                <LocationSelector
                    pickupLocation={pickupLocation}
                    setPickupLocation={setPickupLocation}
                    dropLocation={dropLocation}
                    setDropLocation={setDropLocation}
                />

                {pickupLocation && (

                    <Marker
                        position={[
                            pickupLocation.latitude,
                            pickupLocation.longitude
                        ]}
                    />

                )}

                {dropLocation && (

                    <Marker
                        position={[
                            dropLocation.latitude,
                            dropLocation.longitude
                        ]}
                    />

                )}

            </MapContainer>

        </div>
    );
}

export default MapView;