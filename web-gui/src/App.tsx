import { useContext, useEffect } from "react";
import "./App.css";
import { UserContext } from "@components/UserContext";
import { useNavigate } from "react-router-dom";

function App() {
    const user = useContext(UserContext);
    const navigate = useNavigate();

    // redirect users to the root page corresponding to their role.
    useEffect(() => {
        if (user && user.role === "staff") {
            navigate("/staff/proposed-projects");
        } else if (user && user.role === "student") {
            navigate("/student/available-projects");
        }
    }, [navigate, user]);

    return "";
}

export default App;
