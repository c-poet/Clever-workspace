import { get, post } from "../http";

/** 获取分组权限id */
export const listPermissionId = (itemId: number, type: any) => get({ url: '/api/admin/permissionAcl/listPermissionId', data: { itemId, type } });

/** 保存授权权限信息 */
export const savePermissionAcl = ( data: any ) => post({ url: '/api/admin/permissionAcl/savePermissionAcl', data });
