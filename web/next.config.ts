import type { NextConfig } from "next";

const API_URL = process.env.SERVER_URL;

const nextConfig: NextConfig = {
  /* config options here */
  reactCompiler: true,
  async rewrites() {
    if (!process.env.DEPLOY_TYPE) {
      return [
        {
          source: "/backend/:path*",
          destination: `${API_URL}/:path*`,
        },
      ];
    }
    return [];
  },
};

export default nextConfig;
