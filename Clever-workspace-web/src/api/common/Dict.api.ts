import { get } from "../http";

/** 查询字典 */
export const getDict = (code: string) => get({ url: '/api/common/dict/getDict', data: { code } });
