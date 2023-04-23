// 登录体
export interface LoginDTO {
  username: string;
  password: string;
}

// 认证结果
export interface AuthVO {
  token: string;
}
