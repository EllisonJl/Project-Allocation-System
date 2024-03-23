import { z } from "zod";

export const RoleSchema = z.enum(["staff", "student"]);
export type Role = z.infer<typeof RoleSchema>;

export const UserSchema = z.object({
    username: z.string(),
    name: z.string(),
    role: RoleSchema,
});
export type User = z.infer<typeof UserSchema>;
