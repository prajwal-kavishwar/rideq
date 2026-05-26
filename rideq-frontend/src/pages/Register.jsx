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
                    placeholder="Example: Rahul Sharma"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    className="w-full border p-3 rounded-lg mb-4 placeholder:text-gray-400"
                    required
                />

                <input
                    type="email"
                    placeholder="Example: user@gmail.com"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="w-full border p-3 rounded-lg mb-4 placeholder:text-gray-400"
                    required
                />

                <input
                    type="text"
                    placeholder="10-digit number (Example: 9876543210)"
                    value={phoneNumber}
                    onChange={(e) => setPhoneNumber(e.target.value)}
                    maxLength={10}
                    pattern="[0-9]{10}"
                    title="Enter a valid 10-digit phone number"
                    className="w-full border p-3 rounded-lg mb-4 placeholder:text-gray-400"
                    required
                />

                <input
                    type="password"
                    placeholder="Example: password123"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="w-full border p-3 rounded-lg mb-4 placeholder:text-gray-400"
                    required
                />

                {role === "DRIVER" && (

                    <input
                        type="text"
                        placeholder="Vehicle No. Example: MH20AB0234"
                        value={vehicleNumber}
                        onChange={(e) =>
                            setVehicleNumber(e.target.value.toUpperCase())
                        }
                        pattern="[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}"
                        title="Format: MH20AB0234"
                        className="w-full border p-3 rounded-lg mb-4 placeholder:text-gray-400 uppercase"
                        required
                    />

                )}

                <button
                    type="submit"
                    className="w-full bg-blue-600 hover:bg-blue-700 text-white p-3 rounded-lg transition"
                >
                    Register
                </button>

            </form>

        </div>
    );
}

export default Register;