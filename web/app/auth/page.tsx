"use client";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import React, { useState } from "react";
import { login } from "./action";

import * as z from "zod";
import { Field, FieldDescription } from "@/components/ui/field";

const UserCredentialSchema = z.object({
  email: z.email("Invalid email address"),

  password: z
    .string()
    .min(8, "Password must be at least 8 characters")
    .max(15, "Passord length must not exceed 15 charecters"),
});

export default function SignInCard() {
  const [errors, setErrors] = useState<SignInFormError>({});
  const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    // Handle standard sign-in logic here

    const formData = new FormData(e.currentTarget);

    const formFields = {
      email: formData.get("email")?.toString() || "",
      password: formData.get("password")?.toString() || "",
    };

    const formError: SignInFormError = {};

    const result = UserCredentialSchema.safeParse(formFields);

    if (result.error) {
      for (const iss of result.error.issues) {
        formError[iss.path[0] as keyof SignInFormError] = iss.message;
      }
      setErrors(formError);
      return;
    }

    const userCredentials = result.data;

    const rememberMe = formData.get("rememberMe") ? true : false;

    const serverError = await login(userCredentials, rememberMe);

    setErrors({ error: serverError });
  };

  const handleGoogleLogin = () => {
    // Trigger Google OAuth flow
  };

  return (
    <Card className="w-full max-w-md mx-auto shadow-lg top-1/2 left-1/2 -translate-1/2 absolute">
      <CardHeader className="space-y-1 text-center">
        <CardTitle className="text-2xl font-bold tracking-tight">
          Welcome back
        </CardTitle>
        {errors.error ? (
          <CardDescription className="text-red-600">
            {errors.error}
          </CardDescription>
        ) : (
          <CardDescription>
            Enter your credentials to access your account
          </CardDescription>
        )}
      </CardHeader>
      <CardContent className="space-y-4">
        {/* Social Provider */}
        <Button
          variant="outline"
          type="button"
          className="w-full"
          onClick={handleGoogleLogin}
        >
          <svg
            className="mr-2 h-4 w-4"
            aria-hidden="true"
            focusable="false"
            data-prefix="fab"
            data-icon="google"
            role="img"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 488 512"
          >
            <path
              fill="currentColor"
              d="M488 261.8C488 403.3 391.1 504 248 504 110.8 504 0 393.2 0 256S110.8 8 248 8c66.8 0 123 24.5 166.3 64.9l-67.5 64.9C258.5 52.6 94.3 116.6 94.3 256c0 86.5 69.1 156.6 153.7 156.6 98.2 0 135-70.4 140.8-106.9H248v-85.3h246.1c2.3 12.7 3.9 24.9 3.9 41.4z"
            ></path>
          </svg>
          Continue with Google
        </Button>

        {/* Separator */}
        <div className="relative">
          <div className="absolute inset-0 flex items-center">
            <span className="w-full border-t" />
          </div>
          <div className="relative flex justify-center text-xs uppercase">
            <span className="bg-background px-2 text-muted-foreground">
              Or continue with
            </span>
          </div>
        </div>

        {/* Traditional Credentials Form */}
        <form onSubmit={handleSubmit} className="space-y-4">
          <Field className="space-y-2">
            <Label htmlFor="email">Email</Label>
            <Input
              id="email"
              // type="email"
              name="email"
              placeholder="name@example.com"
              // required
              className={errors.email ? "outline-red-600" : ""}
              onFocus={() =>
                setErrors((pre) => ({
                  ...pre,
                  error: undefined,
                  email: undefined,
                }))
              }
            />
            {errors.email && (
              <FieldDescription className="text-red-600 font-bold">
                {errors.email}
              </FieldDescription>
            )}
          </Field>

          <Field className="space-y-2">
            <Label htmlFor="password">Password</Label>
            <Input
              id="password"
              type="password"
              required
              name="password"
              className={errors.password ? "outline-red-600" : ""}
              onFocus={() =>
                setErrors((pre) => ({
                  ...pre,
                  error: undefined,
                  password: undefined,
                }))
              }
            />
            {errors.password && (
              <FieldDescription className="text-red-600 font-bold">
                {errors.password}
              </FieldDescription>
            )}
          </Field>

          <div className="flex items-center space-x-2 pt-1">
            <Checkbox id="rememberMe" name="rememberMe" />
            <label
              htmlFor="rememberMe"
              className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70 cursor-pointer"
            >
              Remember me
            </label>
          </div>

          <Button type="submit" className="w-full mt-2">
            Sign In
          </Button>
        </form>
      </CardContent>
    </Card>
  );
}

export interface SignInFormError {
  error?: string;
  email?: string;
  password?: string;
}
