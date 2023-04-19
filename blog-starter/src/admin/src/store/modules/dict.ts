import { defineStore } from "pinia";
import { DICT_PREFIX_KEY } from "../keys";
import { Dict } from '@/api/models';
import { listDict } from '@/api/common/Dict.api';

const setDict2stroe = (code: string, dict: any) => {
  localStorage.setItem(DICT_PREFIX_KEY + ':' + code, JSON.stringify(dict));
};

const getDict4stroe = (code: string) => {
  const dictJson = localStorage.getItem(DICT_PREFIX_KEY + ':' + code);
  return dictJson ? JSON.parse(dictJson) : null;
}

// 字典信息
const useDictStore = defineStore("dict", {
  state: () => new Map<string, any>(),
  actions: {
    /** 设置字典值 */
    async setDict(code: string, dict: any) {
      this.set(code, dict);
      setDict2stroe(code, dict);
    },
    /** 获取字典值，本地不存在则从远程获取 */
    async getDict(code: string): Promise<Dict[]> {
      let dict = this.get(code);
      if (!dict) {
        dict = getDict4stroe(code);
        this.set(code, dict);
      }
      if (!dict) {
        const { data } = await listDict(code);
        if (data) {
          this.setDict(code, data);
          dict = data;
        }
      }
      return dict;
    }
  }
});

export default useDictStore;
