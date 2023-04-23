import { defHttp } from '/@/utils/http/axios';
import * as models from './models';

// 获取当前登录用户的信息
export const getPersonInfo = () =>
  defHttp.get<models.PersonInfoVO>({ url: '/common/person/getPersonInfo' });
