import { User } from '../models';

/** 用户信息 */
export interface UserDTO extends User {
    userPass: string;
}

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
    isSingle: boolean;
    hidden: boolean;
    affix: boolean;
    cacheable: boolean;
    description: string;
    children: MenuNodeVO[];
}
