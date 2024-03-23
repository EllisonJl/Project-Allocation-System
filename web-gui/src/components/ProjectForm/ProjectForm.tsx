import { Alert } from "@components/Alert";
import { FormEvent, useState } from "react";
import styled from "styled-components";

type ProjectFormProps = {
    onSubmit: (name: string, description: string) => void;
};

export function ProjectForm({ onSubmit }: ProjectFormProps) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");

    const [errorMessage, setErrorMessage] = useState<string | null>(null);

    function handleSubmit(event: FormEvent) {
        event.preventDefault();

        // validate inputs
        if (name.length === 0) {
            setErrorMessage("Project name cannot be empty.");
            return;
        }
        if (description.length === 0) {
            setErrorMessage("Description cannot be empty.");
            return;
        }

        // clear error when valid
        setErrorMessage(null);

        onSubmit(name, description);
    }

    return (
        <>
            <form onSubmit={handleSubmit}>
                <Label>
                    Project name:
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </Label>
                <Label>
                    Description:
                    <textarea
                        cols={30}
                        rows={4}
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                </Label>
                <button type="submit">Submit</button>
            </form>
            {errorMessage && <Alert title={errorMessage} />}
        </>
    );
}

const Label = styled.label`
    display: block;
`;
