import { PropsWithChildren, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuthToken } from "../../hooks/use-auth-token";

export function RequiresAuth({ children }: PropsWithChildren) {
    const navigate = useNavigate();
    const [token] = useAuthToken();

    useEffect(() => {
        if (token === undefined) {
            navigate("/login");
        }
    }, [token, navigate]);

    return <>{children}</>;
}
