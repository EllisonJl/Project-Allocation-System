import { RequiresAuth } from "@components/RequiresAuth";
import { UserContext } from "@components/UserContext";
import { PropsWithChildren, useContext } from "react";
import { Link } from "react-router-dom";

type Props = {
    role: "staff" | "student";
};

export function RequiresRole({ role, children }: PropsWithChildren<Props>) {
    const user = useContext(UserContext);

    // if the user doesn't exist, the RequiresAuth wrapper will handle it.
    if (user && user.role !== role) {
        return <AccessDeniedPage requiredRole={role} />;
    }

    return <RequiresAuth>{children}</RequiresAuth>;
}

type AccessDeniedPageProps = {
    requiredRole: "staff" | "student";
};

function AccessDeniedPage({ requiredRole }: AccessDeniedPageProps) {
    return (
        <div>
            <h1>Access Denied</h1>

            <p>You must be a {requiredRole} to access this page.</p>

            <p>
                <Link to="/">Go home</Link>
            </p>
        </div>
    );
}
