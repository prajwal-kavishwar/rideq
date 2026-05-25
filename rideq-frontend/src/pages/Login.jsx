import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    async function handleLogin(e) {

        e.preventDefault();

        try {

            const response = await api.post("/api/auth/login", {
                email,
                password,
            });

            localStorage.setItem("token", response.data.token);
            localStorage.setItem("role", response.data.role);

            navigate("/dashboard");

        } catch (error) {

            console.log(error);

            alert("Login failed");
        }
    }

    return (

        <div className="flex items-center justify-center h-screen bg-gray-100">

            <form
                onSubmit={handleLogin}
                className="bg-white p-10 rounded-2xl shadow-lg w-[350px]"
            >

                <h1 className="text-3xl font-bold mb-6 text-center">
                    Login
                </h1>

                <input
                    type="email"
                    placeholder="Enter email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="w-full border p-3 rounded-lg mb-4"
                />

                <input
                    type="password"
                    placeholder="Enter password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="w-full border p-3 rounded-lg mb-6"
                />

                <button
                    type="submit"
                    className="w-full bg-black text-white p-3 rounded-lg"
                >
                    Login
                </button>

            </form>

        </div>
    );
}

export default Login;