import { z } from "zod";

export const userSignupSchema = z.object({
    email: z.string().email("Invalid email"),
   username: z.string().min(3, "Username too short"),
   password: z.string().min(6, "Password too short"),
  })