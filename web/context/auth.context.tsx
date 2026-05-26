"use client";

import { Authorization } from "@/types/user.types";
import { createContext, useContext } from "react";

const AuthContext = createContext<{
  authorization: Authorization | undefined;
}>({
  authorization: undefined,
});

export const AuthContextProvider = ({
  children,
  authorization,
}: {
  children: React.ReactNode;
  authorization: Authorization | undefined;
}) => {
  return (
    <AuthContext.Provider
      value={{
        authorization,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

const useAuthContext = () => {
  return useContext(AuthContext);
};

export default useAuthContext;
