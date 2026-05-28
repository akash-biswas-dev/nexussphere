"use client";
import OptionPicker, { OptionType } from "@/components/option-picker";
import PasswordInputWithToggle from "@/components/password-toggle-input";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Field, FieldDescription, FieldGroup } from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { BASE_URL } from "@/context/user.context";
import axios from "axios";
import { Loader2 } from "lucide-react";
import { useRouter } from "next/navigation";
import { useActionState, useState } from "react";
import * as z from "zod";

const UserDetailsSchema = z
  .object({
    email: z.email("Invalid email"),

    firstName: z.string().min(1, "First name is required"),

    lastName: z.string().min(1, "Last name is required"),

    gender: z.string().min(1, "Gender is required"),

    password: z.string().min(8, "Password must be at least 8 characters"),

    confirmPassword: z.string().min(8, "Confirm password is required"),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  });

export default function SignUpCard() {
  const router = useRouter();

  const [formState, action, isLoading] = useActionState<SignUpForm, FormData>(
    async (formState: SignUpForm, formData: FormData) => {
      // Handle registration logic here

      const userData = {
        email: formData.get("email")?.toString(),
        firstName: formData.get("firstName")?.toString(),
        lastName: formData.get("lastName")?.toString(),
        gender: formData.get("gender")?.toString(),
        password: formData.get("password")?.toString(),
        confirmPassword: formData.get("confirmPassword")?.toString(),
      };

      const result = UserDetailsSchema.safeParse(userData);

      const formError: SignUpFormError = {};

      if (result.error) {
        for (const iss of result.error.issues) {
          formError[iss.path[0] as keyof SignUpFormError] = iss.message;
        }

        return {
          prevState: {
            ...userData,
          },
          errors: formError,
        };
      }

      const userDetails = result.data;

      const res = await axios.post(
        `${BASE_URL}/api/v1/auth/register`,
        {
          email: userDetails.email,
          password: userDetails.password,
          gender: userDetails.gender,
          firstName: userDetails.firstName,
          lastName: userDetails.lastName,
        },
        {
          validateStatus: () => true,
        },
      );

      const { status, data } = res;

      if (status === 201) {
        router.replace("/auth");
        return { prevState: {}, errors: {} };
      }

      const serverError: SignUpFormError = {
        error: data.error ? data.error : "Something went wrong.",
      };

      return {
        prevState: {
          ...userData,
        },
        errors: serverError,
      };
    },
    { prevState: {}, errors: {} },
  );

  const [errors, setErrors] = useState<SignUpFormError>(formState.errors);

  const [prevStateError, setPrevStateError] = useState<SignUpFormError>(
    formState.errors,
  );

  if (formState.errors !== prevStateError) {
    setErrors(formState.errors);
    setPrevStateError(formState.errors);
  }

  const genderOptions: OptionType[] = [
    { key: "MALE", name: "Male" },
    { key: "FEMALE", name: "Female" },
    { key: "OTHER", name: "Other" },
    { key: "PREFER_NOT_SAY", name: "Prefer not to say" },
  ];

  const { prevState } = formState;

  return (
    <Card className="w-full max-w-md mx-auto shadow-lg">
      <CardHeader className="space-y-1 text-center">
        <CardTitle className="text-2xl font-bold tracking-tight">
          Create an account
        </CardTitle>
        {errors.error ? (
          <CardDescription className="text-red-600">
            {errors.error}
          </CardDescription>
        ) : (
          <CardDescription>
            Enter your details below to create your account
          </CardDescription>
        )}
      </CardHeader>
      <CardContent>
        <form action={action} className="space-y-4">
          <div className="grid grid-cols-2 gap-4">
            <Field className="space-y-2">
              <Label htmlFor="firstName">First Name</Label>
              <Input
                defaultValue={prevState.firstName}
                id="firstName"
                placeholder="John"
                required
                name="firstName"
                className={errors.firstName ? "outline-red-600" : ""}
                onFocus={() =>
                  setErrors((pre) => ({
                    ...pre,
                    error: undefined,
                    firstName: undefined,
                  }))
                }
              />
              {errors.firstName && (
                <FieldDescription className="text-red-600 font-bold">
                  {errors.firstName}
                </FieldDescription>
              )}
            </Field>
            <Field className="space-y-2">
              <Label htmlFor="lastName">Last Name</Label>
              <Input
                defaultValue={prevState.lastName}
                id="lastName"
                placeholder="Doe"
                required
                name="lastName"
                className={errors.lastName ? "outline-red-600" : ""}
                onFocus={() =>
                  setErrors((pre) => ({
                    ...pre,
                    error: undefined,
                    lastName: undefined,
                  }))
                }
              />
              {errors.lastName && (
                <FieldDescription className="text-red-600 font-bold">
                  {errors.lastName}
                </FieldDescription>
              )}
            </Field>
          </div>

          <Field className="space-y-2">
            <Label htmlFor="email">Email</Label>
            <Input
              defaultValue={prevState.email}
              id="email"
              type="email"
              name="email"
              placeholder="name@example.com"
              required
            />
          </Field>
          <Field className="space-y-2 w-full">
            <Label htmlFor="gender">Gender</Label>
            <OptionPicker
              selected=""
              options={genderOptions}
              fieldName="gender"
              className={errors.confirmPassword ? "outline-red-600" : ""}
              onSelect={() =>
                setErrors((pre) => ({
                  ...pre,
                  error: undefined,
                  gender: undefined,
                }))
              }
            />
            {errors.gender && (
              <FieldDescription className="text-red-600 font-bold">
                {errors.gender}
              </FieldDescription>
            )}
          </Field>
          <FieldGroup className="flex gap-2 flex-row">
            {/* Password */}
            <Field className="space-y-2">
              <Label htmlFor="password">Password</Label>
              <Input
                defaultValue={prevState.password}
                id="password"
                type="password"
                name="password"
                required
                className={errors.lastName ? "outline-red-600" : ""}
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

            {/* confirmPassword */}
            <Field className="space-y-2">
              <Label htmlFor="confirm-password">Confirm Password</Label>
              <PasswordInputWithToggle
                defaultValue={prevState.confirmPassword}
                id="confirm-password"
                name="confirmPassword"
                className={errors.confirmPassword ? "outline-red-600" : ""}
                onFocus={() =>
                  setErrors((pre) => ({
                    ...pre,
                    error: undefined,
                    confirmPassword: undefined,
                  }))
                }
              />
              {errors.confirmPassword && (
                <FieldDescription className="text-red-600 font-bold">
                  {errors.confirmPassword}
                </FieldDescription>
              )}
            </Field>
          </FieldGroup>

          <Button type="submit" className="w-full mt-2" disabled={isLoading}>
            {isLoading ? (
              <>
                <Loader2 className="animate-spin" />
                Signing Up...
              </>
            ) : (
              "Sign Up"
            )}
          </Button>
        </form>
      </CardContent>
    </Card>
  );
}

export interface SignUpForm {
  errors: SignUpFormError;
  prevState: SignUpFormFields;
}

export interface SignUpFormFields {
  email?: string;
  firstName?: string;
  lastName?: string;
  gender?: string;
  password?: string;
  confirmPassword?: string;
}

export interface SignUpFormError {
  error?: string;
  email?: string;
  password?: string;
  confirmPassword?: string;
  gender?: string;
  firstName?: string;
  lastName?: string;
}
