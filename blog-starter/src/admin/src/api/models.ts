/** 功能 */
export interface Permission {
    id: number | null;
    parentId: number;
    code: string;
    name: string;
    icon: string;
    path: string;
    url: string;
    badgeType: string;
    badge: string;
    isSingle: boolean;
    hidden: boolean;
    affix: boolean;
    cacheable: boolean;
    description: string;
    enabled: boolean;
}
