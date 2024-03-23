import styled from "styled-components";

type AlertProps = {
    title: string;
    description?: string;
};

export function Alert({ title, description }: AlertProps) {
    return (
        <AlertBox>
            <Title>{title}</Title>
            {description && <p>{description}</p>}
        </AlertBox>
    );
}

const AlertBox = styled.div`
    border: 2px solid #dc2626;
    background-color: #fee2e2;
    padding: 1rem 2rem;
    width: fit-content;
`;

const Title = styled.div`
    font-weight: bold;
    font-size: 1.25rem;
`;
