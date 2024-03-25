import { Form } from "@components/Form";
import { TextInput } from "@components/FormInputs";
import { FormEvent, useState } from "react";

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
    }

    return (
        <Form onSubmit={handleSubmit} submitActionText="Log in" error={errorMessage}>
            <TextInput label="Username:" value={username} setValue={setUsername} />
            <TextInput type="password" label="Password:" value={password} setValue={setPassword} />
        </Form>
    );
}
