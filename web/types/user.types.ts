export interface Authorization {
  token: string;
  user: User;
}

export interface User {
  firstName: string;
  lastName: string;
  email: string;
}

export interface UserCredentials {
  email: string;
  password: string;
}

export interface UserDetails {
  email: string;
  firstName: string;
  lastName: string;
  gender: string;
  password: string;
}
