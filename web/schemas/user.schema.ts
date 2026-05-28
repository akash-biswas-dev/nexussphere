import * as z from "zod";

export const UserCredentialSchema = z.object({
  email: z.email("Invalid email address"),
  password: z
    .string()
    .min(8, "Password must be at least 8 characters")
    .max(15, "Passord length must not exceed 15 charecters"),
});
