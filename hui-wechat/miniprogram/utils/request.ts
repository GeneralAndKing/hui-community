export interface RequestOptions extends WechatMiniprogram.RequestOption {
  loading?: boolean
}

export enum HttpMethod {
  GET = 'GET',
  POST = 'POST',
  DELETE = 'DELETE',
  PUT = 'PUT'
}

const http = <T>(url: string, data?: any, method: HttpMethod = HttpMethod.GET, options?: RequestOptions): Promise<T> => {
  const {loading, ...restOptions} = options || {}

  if (loading) {
    wx.showLoading({title: '加载中'})
  }

  const token = wx.getStorageSync('token')
  const headers = {
    'Authorization': token ? `Bearer ${token}` : '',
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    // @ts-ignore
    ...restOptions.headers
  }
  const baseUrl = wx.getStorageSync('baseUrl') || 'http://online.ayue.wiki:8080'
  return new Promise<T>((resolve, reject) => {
    wx.request({
      url: baseUrl + url,
      data,
      method,
      header: headers,
      ...restOptions,
      success: (res) => {
        resolve(res.data as T)
      },
      fail: (err) => {
        console.error(err)
        wx.showToast({
          title: '啊哦，请求数据失败',
          icon: 'error',
          mask: false
        })
        reject(err)
      },
      complete: () => {
        if (loading) {
          wx.hideLoading()
        }
      }
    })
  })
}

export const get = <T>(url: string, data?: any, options?: RequestOptions): Promise<T> => {
  return http<T>(url, data, HttpMethod.GET, options)
}

export const post = <T>(url: string, data?: any, options?: RequestOptions): Promise<T> => {
  return http<T>(url, data, HttpMethod.POST, options)
}

export const del = <T>(url: string, data?: any, options?: RequestOptions): Promise<T> => {
  return http<T>(url, data, HttpMethod.DELETE, options)
}

export const put = <T>(url: string, data?: any, options?: RequestOptions): Promise<T> => {
  return http<T>(url, data, HttpMethod.PUT, options)
}