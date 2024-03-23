import { UserContext } from "@components/UserContext";
import { PropsWithChildren, useContext } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";

export function Layout({ children }: PropsWithChildren) {
    const user = useContext(UserContext);

    return (
        <>
            <Nav>
                <span>
                    Logged in as {user?.name} ({user?.role})
                </span>
                <Link to="/login">
                    Log in as someone else
                </Link>
            </Nav>
            {children}
        </>
    );
}

const Nav = styled.nav`
    border-bottom: 2px solid #ccc;
    padding-block: 0.5rem;
    padding-inline: 2rem;
    display: flex;
    justify-content: end;
    gap: 0.5rem;
`;
