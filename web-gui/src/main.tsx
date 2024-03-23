import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { LoginPage } from "@pages/LoginPage";
import App from "./App";
import { CookiesProvider } from "react-cookie";
import { RequiresAuth } from "@components/RequiresAuth";
import { UserContextProvider } from "@components/UserContext";

const router = createBrowserRouter([
    {
        path: "/login",
        element: <LoginPage />,
    },
    {
        path: "/",
        element: (
            <RequiresAuth>
                <App />
            </RequiresAuth>
        ),
    },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
    <React.StrictMode>
        <CookiesProvider>
            <UserContextProvider>
                <RouterProvider router={router} />
            </UserContextProvider>
        </CookiesProvider>
    </React.StrictMode>,
);
