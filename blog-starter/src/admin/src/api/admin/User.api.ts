import { get, post } from "../http";

/** 查询用户 */
export const getUserById = (userId: number) => get({ url: '/api/admin/user/getUserById', data: { userId } });
/** 查询用户列表 */
export const listUser = (data: any) => get({ url: '/api/admin/user/listUser', data });
/** 新增用户 */
export const insertUser = (data: any) => post({ url: '/api/admin/user/insertUser', data });
/** 更新用户 */
export const updateUser = (data: any) => post({ url: '/api/admin/user/updateUser', data });
/** 删除用户 */
export const deleteUserById = (id: number) => post({ url: '/api/admin/user/deleteUserById', data: { id } });
/** 批量删除用户 */
export const deleteUserByIds = (ids: number[]) => post({ url: '/api/admin/user/deleteUserByIds', data: { ids } });
