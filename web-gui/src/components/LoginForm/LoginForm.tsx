import { FormEvent, useState } from "react";
import styled from "styled-components";

type LoginFormProps = {
    onSubmit: (username: string, password: string) => void;
};

export function LoginForm({ onSubmit }: LoginFormProps) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [errorMessage, setErrorMessage] = useState<string | null>(null);

    function handleSubmit(event: FormEvent) {
        event.preventDefault();

        // validate inputs
        if (username.length === 0) {
            setErrorMessage("Username cannot be empty.");
            return;
        } else if (password.length === 0) {
            setErrorMessage("Password cannot be empty.");
            return;
        }

        // clear error if valid
        setErrorMessage(null);

        onSubmit(username, password);

        resetForm();
    }

    function resetForm() {
        setUsername("");
        setPassword("");
    }

    return (
        <form onSubmit={handleSubmit}>
            <Label>
                Username:
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
            </Label>
            <Label>
                Password:
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </Label>

            <button type="submit">Log in</button>

            {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
        </form>
    );
}

const Label = styled.label`
    display: block;
`;

const ErrorMessage = styled.div`
    color: red;
    font-weight: bold;
`;
