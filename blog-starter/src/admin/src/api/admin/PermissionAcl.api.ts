import { get } from "../http";

/** 获取分组权限id */
export const listPermissionId = (itemId: number, type: any) => get({ url: '/api/admin/permissionAcl/listPermissionId', data: { itemId, type } });
