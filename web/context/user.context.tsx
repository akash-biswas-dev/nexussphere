"use client";
import { Authorization } from "@/types/user.types";
import axios, { AxiosInstance } from "axios";
import { useRouter } from "next/navigation";
import { createContext, useContext, useEffect } from "react";
import useAuthContext from "./auth.context";

export const BASE_URL = process.env.NEXT_PUBLIC_SERVER_URL || "/backend";

const UserContext = createContext<{
  authorization: Authorization;
  axios: AxiosInstance;
}>({
  authorization: {
    token: "",
    user: {
      firstName: "",
      lastName: "",
      email: "",
    },
  },
  axios: axios,
});

export const UserContextProvider = ({
  children,
}: {
  children: React.ReactNode;
}) => {
  const { authorization } = useAuthContext();
  const router = useRouter();

  useEffect(() => {
    if (!authorization) {
      router.push("/auth");
    }
  }, [router, authorization]);

  if (!authorization) {
    return <></>;
  }

  const { token } = authorization;
  const instance = axios.create({
    baseURL: BASE_URL,
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return (
    <UserContext.Provider
      value={{
        authorization,
        axios: instance,
      }}
    >
      {children}
    </UserContext.Provider>
  );
};

export default function useUserContext() {
  return useContext(UserContext);
}
