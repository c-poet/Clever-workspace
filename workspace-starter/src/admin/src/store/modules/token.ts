import { defineStore } from "pinia";
import { USER_TOKEN_KEY } from "../keys";

// 获取本地存储的token
const token: string = localStorage.getItem(USER_TOKEN_KEY) || "";

// token信息
const useTokenStore = defineStore("token", {
  state: () => {
    return {
      token: token
    };
  },
  getters: {
    /** 获取token值 */
    getToken(): string {
      return this.token;
    }
  },
  actions: {
    /** 保存token */
    async setToken(token: string) {
      this.token = token;
      localStorage.setItem(USER_TOKEN_KEY, token);
    },
    /** 清除token  */
    async clear() {
      this.token = '';
      localStorage.removeItem(USER_TOKEN_KEY);
    }
  }
});

export default useTokenStore;
