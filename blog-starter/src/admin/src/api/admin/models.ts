/** 菜单树节点 */
export interface MenuNodeVO {
    id: number;
    parentId: number;
    code: string;
    name: string;
    icon: string;
    path: string;
    url: string;
    badge: string;
    isSingle: string;
    hidden: string;
    affix: boolean;
    cacheable: boolean;
    description: string;
    children: MenuNodeVO[];
}
