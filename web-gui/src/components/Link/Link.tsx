import styled from "styled-components";
import { Link } from "react-router-dom";

export const StyledLink = styled(Link)`
    text-decoration: underline 2px var(--color-cyan-400);
    color: var(--color-cyan-700);
    transition: text-decoration 150ms;
    font-weight: 600;

    &:hover {
        text-decoration: underline 4px var(--color-cyan-500);
        color: var(--color-cyan-900);
    }
`;
