import styled from "styled-components";

type TextAreaInputProps = {
    label: string;
    value: string;
    setValue: (value: string) => void;
};

export function TextAreaInput({ label, value, setValue }: TextAreaInputProps) {
    return (
        <Wrapper>
            <label>{label}</label>
            <TextArea
                rows={3}
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

const TextArea = styled.textarea`
    border-radius: 4px;
    border: 1px solid #aaa;
    width: 100%;
`
