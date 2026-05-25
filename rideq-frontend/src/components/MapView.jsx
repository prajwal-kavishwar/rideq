import { useEffect } from "react";

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

            } else {

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

    useEffect(() => {

        navigator.geolocation.getCurrentPosition(

            (position) => {

                const currentLocation = {
                    latitude: position.coords.latitude,
                    longitude: position.coords.longitude
                };

                if (!pickupLocation) {
                    setPickupLocation(currentLocation);
                }
            },

            (error) => {
                console.log(error);
            }

        );

    }, []);

    return (

        <MapContainer
            center={
                pickupLocation
                    ? [pickupLocation.latitude, pickupLocation.longitude]
                    : [23.2599, 77.4126]
            }
            zoom={13}
            style={{ height: "500px", width: "100%" }}
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
    );
}
export default MapView;