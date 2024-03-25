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
import { Layout } from "@components/Layout";
import {
    StudentProjectPage,
    loader as studentProjectLoader,
} from "@pages/StudentProjectPage";
import {
    StaffProjectPage,
    loader as staffProjectLoader,
} from "@pages/StaffProjectPage";
import { NewProjectPage } from "@pages/NewProjectPage";

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
                <Layout>
                    <Outlet />
                </Layout>
            </RequiresRole>
        ),
        children: [
            {
                path: "proposed-projects",
                element: <ProposedProjectsPage />,
            },
            {
                path: "projects/:projectId",
                element: <StaffProjectPage />,
                loader: staffProjectLoader,
            },
            {
                path: "new-project",
                element: <NewProjectPage />,
            },
        ],
    },
    {
        path: "/student",
        element: (
            <RequiresRole role="student">
                <Layout>
                    <Outlet />
                </Layout>
            </RequiresRole>
        ),
        children: [
            {
                path: "available-projects",
                element: <AvailableProjectsPage />,
            },
            {
                path: "projects/:projectId",
                element: <StudentProjectPage />,
                loader: studentProjectLoader,
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
