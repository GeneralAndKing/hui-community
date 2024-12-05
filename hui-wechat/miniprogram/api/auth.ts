import { post } from "../utils/request"

export const auth = (): Promise<any> =>
  new Promise<any>((resolve, reject) => {
    wx.login({
      success: (loginResult: WechatMiniprogram.LoginSuccessCallbackResult) => {
        console.log(loginResult);
        post<any>('/user-api/login', {code: loginResult.code, username: "admin"})
          .then((result: any) => {
            console.log(result);
            resolve(result)
          })
          .catch((err) => {
            console.log(err);
            reject()
          })
      },
      fail: (err :WechatMiniprogram.GeneralCallbackResult) => {
        console.error('请求登录信息失败', err)
        reject()
      }
    })
  })
