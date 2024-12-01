import type { paths } from '@/types/client'

export interface ResponseError {
  message: string
}


export type AuthToken = typeof paths["/sys-api/login"]["post"]["responses"]["200"]["content"]["*/*"]
