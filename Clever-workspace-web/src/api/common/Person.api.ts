import { get } from "../http";

/** 获取当前登录用户信息 */
export const getPersonInfo = () => get({ url: '/api/common/person/getPersonInfo' });
