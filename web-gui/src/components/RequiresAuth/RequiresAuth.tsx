import { PropsWithChildren, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuthToken } from "../../hooks/use-auth-token";
import { UserContext, UserDispatchContext } from "@components/UserContext";
import { useApiRequest } from "@hooks/use-api-request";
import { User } from "@model/user";

export function RequiresAuth({ children }: PropsWithChildren) {
    const navigate = useNavigate();
    const api = useApiRequest();
    const [token] = useAuthToken();
    const user = useContext(UserContext);
    const setUser = useContext(UserDispatchContext);

    useEffect(() => {
        if (token === undefined) {
            navigate("/login");
        }

        if (user === null) {
            // fetch the details of the logged in user from the API
            fetchUserDetails(api).then((u) => setUser(u));
        }
    }, [token, user, navigate, api, setUser]);

    return <>{children}</>;
}

/**
 * Fetches the details of the currently logged in user from the API.
 *
 * This is used when we have an auth token, but the page has been reloaded so the
 * in-memory state of the current user is lost.
 */
async function fetchUserDetails(
    api: ReturnType<typeof useApiRequest>,
): Promise<User> {
    const body = await api("/me").then((res) => res.json());

    const user: User = {
        username: body.username,
        name: body.name,
        role: body.role,
    };

    return user;
}
