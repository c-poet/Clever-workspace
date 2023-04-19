/** 基础实体 */
export interface BaseEntity {
  id: number | null;
}

/** 用户组 */
export interface Group extends BaseEntity{
  parentId: number;
  code: string;
  name: string;
  enabled: boolean;
  order: number;
  description: string;
}

/** 功能权限 */
export interface Permission extends BaseEntity {
    parentId: number;
    code: string;
    name: string;
    icon: string;
    path: string;
    url: string;
    type: number
    badgeType: number;
    badge: string;
    isSingle: boolean;
    hidden: boolean;
    affix: boolean;
    cacheable: boolean;
    description: string;
    enabled: boolean;
    order: number;
}
