import { Link, useNavigate } from "react-router-dom";

function Navbar() {

    const navigate = useNavigate();

    function handleLogout() {

        localStorage.removeItem("token");

        navigate("/login");
    }

    return (
        <div
            style={{
                padding: "15px",
                borderBottom: "1px solid gray",
                display: "flex",
                gap: "15px"
            }}
        >

            <Link to="/">Home</Link>

            <Link to="/dashboard">Dashboard</Link>

            <Link to="/login">Login</Link>

            <Link to="/register">Register</Link>

            <button onClick={handleLogout}>
                Logout
            </button>

        </div>
    );
}


export default Navbar;