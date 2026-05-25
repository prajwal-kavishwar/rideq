import { Link } from "react-router-dom";

function Home() {
    return (
        <div className="flex flex-col items-center justify-center h-screen gap-6">

            <h1 className="text-5xl font-bold">
                RideQ
            </h1>

            <p className="text-gray-600 text-xl">
                Uber-style ride booking platform
            </p>

            <div className="flex gap-4">

                <Link to="/login">
                    <button className="bg-black text-white px-6 py-3 rounded-xl">
                        Login
                    </button>
                </Link>

                <Link to="/register">
                    <button className="bg-blue-600 text-white px-6 py-3 rounded-xl">
                        Register
                    </button>
                </Link>

            </div>

        </div>
    );
}

export default Home;