import { Form } from "@components/Form";
import { TextAreaInput, TextInput } from "@components/FormInputs";
import { FormEvent, useState } from "react";

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
        <Form
            onSubmit={handleSubmit}
            submitActionText="Submit"
            error={errorMessage}>
            <TextInput label="Project name:" value={name} setValue={setName} />
            <TextAreaInput
                label="Description:"
                value={description}
                setValue={setDescription}
            />
        </Form>
    );
}
