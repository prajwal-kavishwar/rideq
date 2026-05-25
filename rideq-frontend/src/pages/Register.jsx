import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

function Register() {

    const navigate = useNavigate();

    const [role, setRole] = useState("USER");

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [password, setPassword] = useState("");
    const [vehicleNumber, setVehicleNumber] = useState("");

    async function handleRegister(e) {

        e.preventDefault();

        try {

            const requestBody = {
                name,
                email,
                phoneNumber,
                password
            };

            let endpoint = "/api/users";

            if (role === "DRIVER") {

                endpoint = "/api/drivers";

                requestBody.vehicleNumber = vehicleNumber;
            }

            await api.post(endpoint, requestBody);

            alert("Registration successful");

            navigate("/login");

        } catch (error) {

            console.log(error);

            alert("Registration failed");
        }
    }

    return (

        <div className="flex items-center justify-center min-h-screen bg-gray-100 py-10">

            <form
                onSubmit={handleRegister}
                className="bg-white p-10 rounded-2xl shadow-lg w-[400px]"
            >

                <h1 className="text-3xl font-bold mb-6 text-center">
                    Register
                </h1>

                <select
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                    className="w-full border p-3 rounded-lg mb-4"
                >

                    <option value="USER">
                        Rider
                    </option>

                    <option value="DRIVER">
                        Driver
                    </option>

                </select>

                <input
                    type="text"
                    placeholder="Enter name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    className="w-full border p-3 rounded-lg mb-4"
                />

                <input
                    type="email"
                    placeholder="Enter email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="w-full border p-3 rounded-lg mb-4"
                />

                <input
                    type="text"
                    placeholder="Enter phone number"
                    value={phoneNumber}
                    onChange={(e) => setPhoneNumber(e.target.value)}
                    className="w-full border p-3 rounded-lg mb-4"
                />

                <input
                    type="password"
                    placeholder="Enter password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="w-full border p-3 rounded-lg mb-4"
                />

                {role === "DRIVER" && (

                    <input
                        type="text"
                        placeholder="Enter vehicle number"
                        value={vehicleNumber}
                        onChange={(e) => setVehicleNumber(e.target.value)}
                        className="w-full border p-3 rounded-lg mb-4"
                    />

                )}

                <button
                    type="submit"
                    className="w-full bg-blue-600 text-white p-3 rounded-lg"
                >
                    Register
                </button>

            </form>

        </div>
    );
}

export default Register;