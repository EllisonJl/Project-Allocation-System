import { Button } from "@components/Button";
import { FormEventHandler, PropsWithChildren } from "react";
import styled from "styled-components";

type FormProps = {
    onSubmit: FormEventHandler<HTMLFormElement>;
    submitActionText: string;
    error?: string | null;
} & PropsWithChildren;

export function Form({
    onSubmit,
    submitActionText,
    error,
    children,
}: FormProps) {
    return (
        <FormElement onSubmit={onSubmit}>
            {children}

            <Button type="submit">{submitActionText}</Button>

            {error && <ErrorMessage>{error}</ErrorMessage>}
        </FormElement>
    );
}

const FormElement = styled.form`
    display: grid;

    & ${Button} {
        justify-self: center;
    }
`

const ErrorMessage = styled.div`
    color: red;
    font-weight: bold;
    justify-self: center;
    margin-top: 1rem;
`;
