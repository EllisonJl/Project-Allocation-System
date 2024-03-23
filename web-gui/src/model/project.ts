import { User } from "./user";

export type Project = {
    id: number;
    name: string;
    description: string;
    proposed_by: User;
    assigned_to: User | null;
    interested_students: User[];
}
