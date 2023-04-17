import { get, post } from "../http";

/** 保存功能权限 */
export const save = (data: any) => post({url: '/api/admin/permission/save', data});
