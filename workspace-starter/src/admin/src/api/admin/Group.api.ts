import { get, post } from "../http";

/** 查询用户组 */
export const getGroupById = (id: number) => get({ url: '/api/admin/group/getGroupById', data: { id } });
/** 查询用户组列表 */
export const listGroup = (data: any) => get({ url: '/api/admin/group/listGroup', data });
/** 查询用户组树 */
export const listGroupTree = (data: any) => get({ url: '/api/admin/group/listGroupTree', data });
/** 新增用户组 */
export const insertGroup = (data: any) => post({ url: '/api/admin/group/insertGroup', data });
/** 更新用户组 */
export const updateGroup = (data: any) => post({ url: '/api/admin/group/updateGroup', data });
/** 删除用户组 */
export const deleteGroupById = (id: number) => post({ url: '/api/admin/group/deleteGroupById', data: { id } });
/** 删除用户组 */
export const deleteGroupByIds = (ids: number[]) => post({ url: '/api/admin/group/deleteGroupByIds', data: { ids } });
