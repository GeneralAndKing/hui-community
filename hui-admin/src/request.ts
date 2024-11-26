import createClient from "openapi-fetch";
import type { paths } from "./types/client";

export const client = createClient<paths>({
  baseUrl: "http://139.155.2.12:8080"
});
