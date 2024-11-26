import { ref, computed } from "vue";
import { defineStore } from "pinia";
import type { components, paths } from '@/types/client'

const useApplicationStore = defineStore("counter", () => {
  const token = ref<paths['/sys-api/login']['post']['responses']['200']['content']["*/*"]>()
  return {};
}, {
  persist: true
})


export default useApplicationStore
