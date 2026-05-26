"use server";

import { SESSION } from "@/constants/cookie";
import { Authorization } from "@/types/user.types";
import axios from "axios";
import { cookies } from "next/headers";

const SERVER_URL = process.env.SERVER_URL || "http://localhost:9000";

export async function fetchAuthorization(): Promise<undefined | Authorization> {
  const cookieStore = await cookies();

  const session = cookieStore.get(SESSION);

  if (!session) {
    return undefined;
  }

  try {
    const res = await axios.get(`${SERVER_URL}/api/v1/auth/authorization`, {
      headers: {
        Authorization: `Bearer ${session.value}`,
      },
      validateStatus: () => true,
    });

    const { status, data } = res;

    if (status !== 200) {
      return undefined;
    }

    return data;
  } catch (err) {
    console.error(err);
  }

  return undefined;
}
