const image = 'https://tdesign.gtimg.com/mobile/demos/example2.png'
const items = new Array(12).fill({label: '标题文字', image}, 0, 12)

Page({
  offsetTopList: [0],
  data: {
    sideBarIndex: 1,
    scrollTop: 0,
    categories: [
      {
        label: '店铺福利',
        title: '店铺福利',
        badgeProps: {},
        items
      },
      {
        label: '甄选套餐',
        title: '甄选套餐',
        badgeProps: {
          dot: true
        },
        items: items.slice(0, 9)
      },
      {
        label: '超值优惠',
        title: '超值优惠',
        badgeProps: {},
        items: items.slice(0, 9)
      },
      {
        label: '甜点小吃',
        title: '甜点小吃',
        badgeProps: {
          count: 6
        },
        items: items.slice(0, 6)
      }
    ],
    navbarHeight: 0
  },
  onLoad() {
    this.offsetTopList = []
    const query = wx.createSelectorQuery().in(this)
    const {sideBarIndex} = this.data
    query.selectAll('.title').boundingClientRect()
    query.select('.custom-navbar').boundingClientRect()
    const navbarHeight = this.getNavbarHeight()
    query.exec((res: [WechatMiniprogram.BoundingClientRectCallbackResult[], WechatMiniprogram.BoundingClientRectCallbackResult]) => {
      const [rects] = res
      this.offsetTopList = rects.map((item: WechatMiniprogram.BoundingClientRectCallbackResult) => (item.top - navbarHeight) as number)
      this.setData({scrollTop: this.offsetTopList[sideBarIndex]})
    })
  },
  getNavbarHeight() {
    const windowInfo = wx.getWindowInfo()
    const menuButtonInfo = wx.getMenuButtonBoundingClientRect()
    const statusBarHeight = windowInfo.statusBarHeight
    const menuBottomPosition = menuButtonInfo.bottom
    const navbarHeight = menuBottomPosition + (menuButtonInfo.top - statusBarHeight)

    this.setData({navbarHeight})
    return navbarHeight
  },
  onSideBarChange(e: { detail: { value: number } }) {
    const {value} = e.detail
    this.setData({sideBarIndex: value, scrollTop: this.offsetTopList[value]})
  },
  onScroll(e: WechatMiniprogram.ScrollViewScroll) {
    const {scrollTop} = e.detail
    const threshold = 50 // 下一个标题与顶部的距离

    if (scrollTop < threshold) {
      this.setData({sideBarIndex: 0})
      return
    }

    const index = this.offsetTopList.findIndex((top) => top > scrollTop && top - scrollTop <= threshold)

    if (index > -1) {
      this.setData({sideBarIndex: index})
    }
  }
})
