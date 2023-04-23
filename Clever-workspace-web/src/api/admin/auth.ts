import { defHttp } from '/@/utils/http/axios';
import { ErrorMessageMode } from '/#/axios';
import * as models from './models';

// 用户登录认证
export const login = (params: models.LoginDTO, mode: ErrorMessageMode = 'modal') =>
  defHttp.post<models.AuthVO>({ url: '/admin/auth/login', params }, { errorMessageMode: mode });
