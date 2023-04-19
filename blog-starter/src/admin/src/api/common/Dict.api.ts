import { get } from "../http";

/** 查询字典 */
export const getDict = (code: string) => get({ url: '/api/common/dict/getDict', data: { code } });

/** 获取字典项列表 */
export const listDict = (code: string) => get({ url: '/api/common/dict/listDict', data: { code } });
