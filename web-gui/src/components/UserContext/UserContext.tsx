import { PropsWithChildren, createContext, useState } from "react";
import { User } from "@model/user";

export const UserContext = createContext<User | null>(null);
export const UserDispatchContext = createContext<
    React.Dispatch<React.SetStateAction<User | null>>
>(() => {});

export function UserContextProvider({ children }: PropsWithChildren) {
    const [user, setUser] = useState<User | null>(null);

    return (
        <UserContext.Provider value={user}>
            <UserDispatchContext.Provider value={setUser}>
                {children}
            </UserDispatchContext.Provider>
        </UserContext.Provider>
    );
}
