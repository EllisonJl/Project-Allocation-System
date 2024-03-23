import { PropsWithChildren, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuthToken } from "../../hooks/use-auth-token";
import { UserContext } from "@components/UserContext";

export function RequiresAuth({ children }: PropsWithChildren) {
    const navigate = useNavigate();
    const [token] = useAuthToken();
    const user = useContext(UserContext);

    useEffect(() => {
        if (token === undefined || user === null) {
            navigate("/login");
        }
    }, [token, user, navigate]);

    return <>{children}</>;
}
