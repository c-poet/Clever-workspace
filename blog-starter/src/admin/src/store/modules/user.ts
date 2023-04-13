import LayoutStore from "@/layouts";
import Cookies from "js-cookie";

import Avatar from "@/assets/img_avatar.gif";
import { defineStore } from "pinia";
import { UserState } from "../types";
import { getPersonInfo } from '@/api/Person.api';
import {
  ROLE_ID_KEY,
  USER_ID_KEY,
  USER_INFO_KEY,
  USER_TOKEN_KEY,
} from "../keys";

const defaultAvatar = Avatar;

const token = Cookies.get(USER_TOKEN_KEY);

const userInfo: UserState = JSON.parse(
  localStorage.getItem(USER_INFO_KEY) || "{}"
);

const useUserStore = defineStore("user", {
  state: () => {
    return {
      isInit: false,
      userId: userInfo.userId || 0,
      roleId: userInfo.roleId || 0,
      roles: userInfo.roles || null,
      token: token || "",
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
    /** 保存token */
    async setToken(token: string) {
      this.token = token;
      Cookies.set(USER_TOKEN_KEY, token);
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
        this.isInit = false;
        this.userId = 0;
        this.avatar = "";
        this.roleId = 0;
        this.roles = [];
        this.userName = "";
        this.nickName = "";
        this.token = "";
        Cookies.remove(USER_TOKEN_KEY);
        localStorage.clear();
        LayoutStore.reset();
        res();
      });
    },
  },
});

export default useUserStore;
