import { defineStore } from "pinia";
import useAuthStore from "@/stores/auth";
import { computed, ref } from "vue";
import type { components } from "@/types/client";
import { useRouter } from "vue-router";
import { client } from "@/request";
import type { Community } from "@/types";

const useApplicationStore = defineStore(
  "application",
  () => {
    const authStore = useAuthStore();
    const information = ref<components["schemas"]["SysUserResponse"]>();
    const router = useRouter();
    const currentCommunity = ref<Community | undefined>();

    const getUserInfo = async () => {
      if (!authStore.isAuth) {
        await router.push("/auth");
        return;
      }
      const { data } = await client.GET("/sys-api/sys-user/my");
      information.value = data;
    };

    const communityList = computed(() => {
      if (!information.value?.roles) {
        return [];
      }
      return information.value.roles.map(
        role =>
          ({
            name: role.communityName,
            id: role.communityId
          }) as Community
      );
    });

    const setCurrentCommunity = (community: Community) => {
      currentCommunity.value = community;
    };

    return {
      getUserInfo,
      setCurrentCommunity,
      information,
      communityList,
      currentCommunity
    };
  },
  {
    persist: true
  }
);

export default useApplicationStore;
