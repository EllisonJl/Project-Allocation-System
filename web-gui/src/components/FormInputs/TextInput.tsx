import { useId } from "react";
import styled from "styled-components";

type TextInputProps = {
    label: string;
    value: string;
    setValue: (value: string) => void;
    type?: "text" | "password";
};

export function TextInput({ label, value, setValue, type }: TextInputProps) {
    const inputId = useId();

    return (
        <Wrapper>
            <label htmlFor={inputId}>{label}</label>
            <Input
                id={inputId}
                type={type || "text"}
                value={value}
                onChange={(e) => setValue(e.target.value)}
            />
        </Wrapper>
    );
}

const Wrapper = styled.div`
    padding-bottom: 1rem;

    & * {
        display: block;
    }
`;

const Input = styled.input`
    border-radius: 4px;
    border: 1px solid #aaa;
    width: 100%;
    padding: 0.25rem 0.5rem;
`;
