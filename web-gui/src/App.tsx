import { useContext } from "react";
import "./App.css";
import { UserContext } from "@components/UserContext";

function App() {
    const user = useContext(UserContext);

    return (
        <>
            User: {JSON.stringify(user)}
        </>
    );
}

export default App;
