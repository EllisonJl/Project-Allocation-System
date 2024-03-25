import { useNavigate } from "react-router-dom";
import { useAuthToken } from "./use-auth-token";

const API_URL = "http://localhost:8080";

export function useApiRequest() {
    const [token] = useAuthToken();
    const navigate = useNavigate();

    const fetchFn = async (route: string, opts?: RequestInit) => {
        if (opts === undefined) {
            opts = {};
        }

        // add the authorization header for all routes that aren't login
        if (route !== "/login") {
            // if not authenticated, redirect to login page
            if (!token) {
                navigate("/login");
            }

            opts = {
                ...opts,
                headers: {
                    ...opts.headers,
                    Authorization: `Bearer ${token}`,
                },
            };
        }

        return await fetch(`${API_URL}${route}`, opts);

        // TODO: automatically redirect to login if fetch returns Unauthorized
    };

    return fetchFn;
}
