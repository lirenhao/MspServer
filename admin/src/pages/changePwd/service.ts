import request from '@/utils/request';
import { ModifyData } from './data';

export async function changePwd(params: ModifyData) {
  return request('/admin/apis/change_pwd', {
    method: 'POST',
    data: params,
  });
}
