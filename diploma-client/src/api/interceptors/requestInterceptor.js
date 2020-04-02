import { pipe } from '@/util/functions'
import { addCsrfToken } from '@/api/interceptors/addCsrfToken'

const intercept = pipe(addCsrfToken)

export const requestInterceptor = (config) => {
  return intercept(config)
}