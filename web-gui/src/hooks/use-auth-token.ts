import { useCallback } from "react";
import { useCookies } from "react-cookie";

const TOKEN_COOKIE = "token";

export function useAuthToken() {
    const [cookies, setCookie] = useCookies([TOKEN_COOKIE]);

    const setToken = useCallback(
        (token: string) => {
            // make cookie accessible from all pages, and have it live for an hour
            // (same length as in the backend)
            setCookie(TOKEN_COOKIE, token, { path: "/", maxAge: 3600 });
        },
        [setCookie],
    );

    return [cookies.token, setToken];
}
