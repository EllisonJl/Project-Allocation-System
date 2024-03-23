import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { Outlet, RouterProvider, createBrowserRouter } from "react-router-dom";
import { LoginPage } from "@pages/LoginPage";
import App from "./App";
import { CookiesProvider } from "react-cookie";
import { RequiresAuth } from "@components/RequiresAuth";
import { UserContextProvider } from "@components/UserContext";
import { ProposedProjectsPage } from "@pages/ProposedProjectsPage";
import { AvailableProjectsPage } from "@pages/AvailableProjectsPage";
import { RequiresRole } from "@components/RequiresRole";

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
    {
        path: "/staff",
        element: (
            <RequiresRole role="staff">
                <Outlet />
            </RequiresRole>
        ),
        children: [
            {
                path: "proposed-projects",
                element: <ProposedProjectsPage />,
            },
        ],
    },
    {
        path: "/student",
        element: (
            <RequiresRole role="student">
                <Outlet />
            </RequiresRole>
        ),
        children: [
            {
                path: "available-projects",
                element: <AvailableProjectsPage />,
            },
        ],
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
