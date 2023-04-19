/** 功能权限 */
export interface Permission {
    id: number | null;
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
