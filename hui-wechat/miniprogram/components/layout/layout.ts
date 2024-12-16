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
    communityVisible: false,
    communityValue: [],
    communityList: [
      { label: '超级小区', value: '超级小区' },
      { label: '观山小区', value: '观山小区' }
    ]
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
    },
    onPickerChange(e: any) {
      console.log(1)
      console.log(e)
      this.setData({ communityVisible: false });
    },
    onColumnChange(e: any) {
      console.log(2)
      console.log(e)
    },
    onPickerCancel() {
      this.setData({ communityVisible: false });
    },
    onSelect () {
      console.log('test')
      this.setData({ communityVisible: true });
    }
  }
})
