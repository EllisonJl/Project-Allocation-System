import { LoginForm } from "@components/LoginForm";
import { UserDispatchContext } from "@components/UserContext";
import { useApiRequest } from "@hooks/use-api-request";
import { useAuthToken } from "@hooks/use-auth-token";
import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

export function LoginPage() {
    const api = useApiRequest();
    const [_token, setToken] = useAuthToken();
    const navigate = useNavigate();
    const setUser = useContext(UserDispatchContext);

    const [errorMessage, setErrorMessage] = useState<string | null>(null);

    async function login(username: string, password: string) {
        const response = await api("/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                username,
                password,
            }),
        });

        if (response.status === 401) {
            // Unauthorized
            setErrorMessage("Invalid login details. Please try again.");
            return;
        } else if (!response.ok) {
            // Catch any other error
            setErrorMessage("Unknown error. Please try again.");
            return;
        }

        // clear error if success
        setErrorMessage(null);

        // store the auth token we got
        const body = await response.json();
        setToken(body.token);

        // TODO: persist user details in localStorage or get them from the API if we need them
        setUser({
            username,
            name: body.name,
            role: body.role,
        });

        navigate("/");
    }

    return (
        <>
            <h1>Log in</h1>

            <LoginForm onSubmit={login} />

            {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
        </>
    );
}

const ErrorMessage = styled.div`
    color: red;
    font-weight: bold;
`;
