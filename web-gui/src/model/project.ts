import { z } from "zod";
import { UserSchema } from "./user";

export const ProjectSchema = z.object({
    id: z.number().int(),
    name: z.string(),
    description: z.string(),
    proposed_by: UserSchema,
    assigned_to: UserSchema.nullable(),
    interested_students: UserSchema.array().default([]),
});
export type Project = z.infer<typeof ProjectSchema>;
