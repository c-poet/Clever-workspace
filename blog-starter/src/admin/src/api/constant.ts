import * as models from '@/api/models';

/** 默认id值 */
export const DEFAULT_ID = -1;

/** 默认父级id值 */
export const DEFAULT_PID = DEFAULT_ID;

/** 默认排序值 */
export const DEFAULT_ORDER = 99;

/** 校验数字字母正则 */
export const CHECK_REGEX00 = '^[a-zA-Z0-9]+$';

/** 校验数字字母、下划线、-正则 */
export const CHECK_REGEX01 = '^[a-zA-Z0-9_-]+$';

/** 授权类型 */
export const PermissionAclType = {
  GROUP_PERMISSION: { id: 1, desc: '用户组权限' },
  PERSON_PERMISSION: { id: 2, desc: '用户权限' }
};

/** 分组默认值 */
export const DEFAULT_GROUP: models.Group = {
  id: null,
  parentId: DEFAULT_PID,
  code: '',
  name: '',
  enabled: true,
  order: DEFAULT_ORDER,
  description: '',
}

/** 权限默认值 */
export const DEFAULT_PERMISSION: models.Permission = {
  id: null,
  parentId: DEFAULT_PID,
  code: '',
  name: '',
  icon: '',
  path: '',
  url: '',
  badgeType: -1,
  badge: '',
  isSingle: false,
  hidden: false,
  affix: false,
  cacheable: false,
  description: '',
  enabled: true,
  order: DEFAULT_ORDER,
  type: -1,
}

