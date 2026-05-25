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

        <div className="min-h-screen flex items-center justify-center px-6">

            <form
                onSubmit={handleLogin}
                className="bg-white p-10 rounded-3xl shadow-xl w-full max-w-md"
            >

                <h1 className="text-4xl font-bold mb-2">
                    Welcome Back
                </h1>

                <p className="text-gray-500 mb-8">
                    Login to continue using RideQ
                </p>

                <input
                    type="email"
                    placeholder="Enter email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="w-full border border-gray-300 p-4 rounded-2xl mb-4 outline-none focus:border-black"
                />

                <input
                    type="password"
                    placeholder="Enter password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="w-full border border-gray-300 p-4 rounded-2xl mb-6 outline-none focus:border-black"
                />

                <button
                    type="submit"
                    className="w-full bg-black text-white p-4 rounded-2xl text-lg font-semibold hover:bg-zinc-800 transition"
                >
                    Login
                </button>

            </form>

        </div>
    );
}

export default Login;