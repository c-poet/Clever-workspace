/** 字典 */
export interface Dict {
  code: string;
  label: string;
  value: string;
  desc: string;
}

/** 功能权限 */
export interface Permission {
    id: number | null;
    parentId: number;
    code: string;
    name: string;
    icon: string;
    path: string;
    url: string;
    type: string
    badgeType: string;
    badge: string;
    isSingle: boolean;
    hidden: boolean;
    affix: boolean;
    cacheable: boolean;
    description: string;
    enabled: boolean;
    order: number;
}
