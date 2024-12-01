import createClient from "openapi-fetch";
import type { paths } from "./types/client";

export const client = createClient<paths>({
  baseUrl: import.meta.env.VITE_SERVICE_BASE_URL
});
