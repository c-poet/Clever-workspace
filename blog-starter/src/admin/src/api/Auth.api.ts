import { post, Response } from "./http";

/**
 * 用户登录
 * 
 * @param data 
 * @returns 
 */
export const login = (data: any) => post({ url: '/api/auth/login', data });
