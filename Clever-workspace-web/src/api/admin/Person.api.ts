import { get } from "../http";

/** 获取当前登录用户的菜单树 */
export const listMenu = () => get({ url: '/api/admin/person/listMenu' });
