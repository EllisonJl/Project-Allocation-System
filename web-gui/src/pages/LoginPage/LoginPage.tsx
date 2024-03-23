import styled from "styled-components";

const Label = styled.label`
    display: block;
`

export function LoginPage() {
    return (
        <>
            <h1>Log in</h1>

            <form>
                <Label>
                    Username:
                    <input type="text" />
                </Label>
                <Label>
                    Password:
                    <input type="password" />
                </Label>
            </form>
        </>
    );
}
