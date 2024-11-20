// components/layout/layout.ts
Component({
  options: {
    styleIsolation: 'shared'
  },
  /**
   * 组件的属性列表
   */
  properties: {
    title: String,
    value: String,
    nav: {
      type: Boolean,
      default: true
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    list: [
      { value: '/pages/home/home', icon: 'shop', ariaLabel: '商家' },
      { value: '/pages/user/user', icon: 'user', ariaLabel: '用户' }
    ],
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onChange(e: any) {
      console.log(e.detail.value);
      wx.redirectTo({
        url: e.detail.value
      })
    }
  }
})