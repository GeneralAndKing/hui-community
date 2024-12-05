import type { components, paths } from "@/types/client";

export interface ResponseError {
  message?: string;
  error?: string;
}

export interface Community {
  id?: string;
  name?: string;
}

export interface AsideMenu {
  title: string;
  name: string;
  icon: string;
}

export type AuthToken = (typeof paths)["/sys-api/login"]["post"]["responses"]["200"]["content"]["*/*"];
export type NewOrEditUser = components["schemas"]["AddSysUserRequest"] & {
  id?: string;
};
