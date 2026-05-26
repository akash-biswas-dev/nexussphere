"use server";

import { SESSION } from "@/constants/cookie";
import { UserCredentials } from "@/types/user.types";
import axios from "axios";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

const SERVER_URL = process.env.SERVER_URL || "http://localhost:9000";

export async function login(
  userCredentials: UserCredentials,
  rememberMe: boolean,
): Promise<string> {
  const res = await axios.post(`${SERVER_URL}/api/v1/auth`, userCredentials, {
    params: {
      rememberMe,
    },
    validateStatus: () => true,
  });

  const { data, status } = res;

  if (status !== 201) {
    if (data.error) {
      return data.error;
    }
    return "Some internal error occurred";
  }

  const cookieStore = await cookies();

  cookieStore.set(SESSION, data.token, {
    maxAge: data.maxAge,
  });

  redirect("/dashboard");
}

export async function logout() {
  const cookieStore = await cookies();
  cookieStore.delete(SESSION);
  redirect("/auth");
}
