"use server";

import { SESSION } from "@/constants/cookie";
import { UserCredentialSchema } from "@/schemas/user.schema";
import axios from "axios";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";
import { SignInForm, SignInFormError } from "./page";

const SERVER_URL = process.env.SERVER_URL || "http://localhost:9000";

export async function login(
  prevState: SignInForm,
  formData: FormData,
): Promise<SignInForm> {
  const formFields = {
    email: formData.get("email")?.toString() || "",
    password: formData.get("password")?.toString() || "",
  };

  const result = UserCredentialSchema.safeParse(formFields);
  const rememberMe = formData.get("rememberMe") ? true : false;

  if (result.error) {
    const formError: SignInFormError = {};
    for (const iss of result.error.issues) {
      formError[iss.path[0] as keyof SignInFormError] = iss.message;
    }
    return {
      prevState: {
        ...formFields,
        rememberMe,
      },
      errors: formError,
    };
  }

  const res = await axios.post(`${SERVER_URL}/api/v1/auth`, result.data, {
    params: {
      rememberMe,
    },
    validateStatus: () => true,
  });

  const { data, status } = res;

  if (status !== 201) {
    const err: SignInFormError = {
      error: data.error || "Something went wrong.",
    };
    return {
      prevState: {
        ...formFields,
        rememberMe,
      },
      errors: err,
    };
  }

  const cookieStore = await cookies();

  cookieStore.set(SESSION, data.token, {
    maxAge: data.maxAge,
  });

  redirect("/workspaces");
}

export async function logout() {
  const cookieStore = await cookies();
  cookieStore.delete(SESSION);
  redirect("/auth");
}
