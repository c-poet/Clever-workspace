import LayoutStore from "@/layouts";
import { getPersonInfo } from '@/api/Person.api';
import Avatar from "@/assets/img_avatar.gif";
import { defineStore } from "pinia";
import { USER_INFO_KEY } from "../keys";
import { UserState } from "../types";
import pinia from '@/store/pinia';
import useTokenStore from "@/store/modules/token";

const tokenStore = useTokenStore(pinia);
const defaultAvatar = Avatar;

// 获取保存的本地用户信息
const userInfo: UserState = JSON.parse(
  localStorage.getItem(USER_INFO_KEY) || "{}"
);

// 用户信息
const useUserStore = defineStore("user", {
  state: () => {
    return {
      isInit: false,
      userId: userInfo.userId || 0,
      roleId: userInfo.roleId || 0,
      roles: userInfo.roles || null,
      userName: userInfo.userName || "",
      nickName: userInfo.nickName || "",
      avatar: userInfo.avatar || defaultAvatar,
    };
  },
  getters: {
    getUserId(): number {
      return this.userId;
    },
    getRroleId(): number {
      return this.roleId;
    },
  },
  actions: {
    /** 初始化当前用户信息 */
    async initUser() {
      const { data } = await getPersonInfo();
      this.saveUser(data as UserState);
      this.isInit = true;
    },
    /** 记录当前用户信息 */
    saveUser(userInfo: UserState) {
      return new Promise<void>((res) => {
        this.userId = userInfo.userId;
        this.userId = userInfo.userId;
        this.roleId = userInfo.roleId;
        this.roles = userInfo.roles;
        this.userName = userInfo.userName;
        this.nickName = userInfo.nickName;
        this.avatar = userInfo.avatar || defaultAvatar;
        localStorage.setItem(USER_INFO_KEY, JSON.stringify(userInfo));
        res();
      });
    },
    /** 变更当前用户昵称 */
    changeNickName(newNickName: string) {
      this.nickName = newNickName;
    },
    /** 用户登出 */
    logout() {
      return new Promise<void>((res) => {
        tokenStore.clear();
        this.isInit = false;
        this.userId = 0;
        this.avatar = '';
        this.roleId = 0;
        this.roles = [];
        this.userName = '';
        this.nickName = '';
        localStorage.clear();
        LayoutStore.reset();
        res();
      });
    },
  },
});

export default useUserStore;
