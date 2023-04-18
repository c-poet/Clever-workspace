import { get, post } from "../http";

/** 根据id查询权限信息 */
export const getPermissionById = (id: number) => get({ url: '/api/admin/permision/getPermissionById', data: { id } });

/** 获取权限列表 */
export const listPermission = (data: any) => get({ url: '/api/admin/permission/listPermission', data });

/** 新增功能权限 */
export const insertPermission = (data: any) => post({ url: '/api/admin/permission/insertPermission', data });

/** 更新功能权限 */
export const updatePermission = (data: any) => post({ url: '/api/admin/permission/updatePermission', data });

/** 根据id删除权限 */
export const deletePermissionById = (id: number) => post({ url: '/api/admin/permisison/deletePermissionById', data: { id } });

/** 批量删除权限 */
export const batchDeletePermission = (ids: number[]) => post({ url: '/api/admin/permisin/batchDeletePermission', data: ids }); 
