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
import { useActionState, useState } from "react";
import { login } from "./action";

import { Field, FieldDescription } from "@/components/ui/field";
import { Loader2 } from "lucide-react";

export default function SignInCard() {
  const [formState, action, isLoading] = useActionState<SignInForm, FormData>(
    login,
    {
      prevState: {},
      errors: {},
    },
  );
  const [errors, setErrors] = useState<SignInFormError>(formState.errors);
  const [prevStateError, setPrevStateError] = useState<SignInFormError>(
    formState.errors,
  );

  if (formState.errors !== prevStateError) {
    setErrors(formState.errors);
    setPrevStateError(formState.errors);
  }

  const handleGoogleLogin = () => {
    // Trigger Google OAuth flow
  };

  const { prevState } = formState;

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
        <form action={action} className="space-y-4">
          <Field className="space-y-2">
            <Label htmlFor="email">Email</Label>
            <Input
              defaultValue={prevState.email}
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
              defaultValue={prevState.password}
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
            <Checkbox
              id="rememberMe"
              name="rememberMe"
              defaultChecked={
                prevState.rememberMe ? "indeterminate" : undefined
              }
            />
            <label
              htmlFor="rememberMe"
              className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70 cursor-pointer"
            >
              Remember me
            </label>
          </div>

          <Button type="submit" className="w-full mt-2" disabled={isLoading}>
            {isLoading ? (
              <>
                <Loader2 className="animate-spin" />
                Signing In...
              </>
            ) : (
              "Sign In"
            )}
          </Button>
        </form>
      </CardContent>
    </Card>
  );
}

export interface SignInForm {
  errors: SignInFormError;
  prevState: SignInFormFields;
}

export interface SignInFormFields {
  email?: string;
  password?: string;
  rememberMe?: boolean;
}

export interface SignInFormError {
  error?: string;
  email?: string;
  password?: string;
}
