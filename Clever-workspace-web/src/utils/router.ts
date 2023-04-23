import LayoutStore, { Layout } from "@/layouts";
import { isExternal, mapTwoLevelRouter } from "@/layouts/utils";
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import router, { asyncRoutes, constantRoutes } from "../router";
import { listMenu } from "@/api/admin/Person.api";
import { MenuNodeVO } from '@/api/admin/models';
import { RouteRecordRaw } from "vue-router";
import { RouteRecordRawWithHidden } from "@/layouts/types";
import useUserStore from "@/store/modules/user";
import useTokenStore from "@/store/modules/token";
import pinia from "@/store/pinia";

const userStore = useUserStore(pinia);
const tokenStore = useTokenStore(pinia);

NProgress.configure({
  showSpinner: false,
});

async function getRoutes() {
  const { data } = await listMenu();
  console.log(data);
  return generatorRoutes( data );
}

function getComponent(it: MenuNodeVO) {
  return (): any => import("@/views" + it.path + ".vue");
}

function isMenu(route: MenuNodeVO) {
  return route.children && route.children.length > 0;
}

function generatorRoutes(res: Array<MenuNodeVO>) {
  const tempRoutes: Array<RouteRecordRawWithHidden> = [];
  res.forEach((it) => {
    const route: RouteRecordRawWithHidden = {
      path: it.url && isExternal(it.url) ? it.url : it.path,
      name: it.code,
      hidden: !!it.hidden,
      component: isMenu(it) ? Layout : getComponent(it),
      children: [],
      meta: {
        title: it.name,
        affix: !!it.affix,
        cacheable: !!it.cacheable,
        icon: it.icon || "",
        badge: it.badge,
        isSingle: !!it.isSingle,
      },
    };
    if (it.children) {
      route.children = generatorRoutes(it.children);
    }
    tempRoutes.push(route);
  });
  return tempRoutes;
}

const whiteRoutes: string[] = ["/login"];

function isTokenExpired(): boolean {
  return !!tokenStore.getToken;
}

router.beforeEach(async (to) => {
  NProgress.start();
  if (whiteRoutes.includes(to.path)) {
    NProgress.done();
    return true;
  } else {
    if (!isTokenExpired()) {
      NProgress.done();
      return {
        path: "/login",
        query: { redirect: to.fullPath },
      };
    } else {
      // 判断用户信息是否已经初始化
      if (!userStore.isInit) {
        userStore.initUser();
      }
      const isEmptyRoute = LayoutStore.isEmptyPermissionRoute();
      if (isEmptyRoute) {
        // 加载路由
        const accessRoutes: Array<RouteRecordRaw> = [];
        const tempRoutes = await getRoutes();
        accessRoutes.push(...tempRoutes);
        accessRoutes.push({
          path: "/:pathMatch(.*)*",
          redirect: "/404",
          hidden: true,
        } as RouteRecordRaw);
        const mapRoutes = mapTwoLevelRouter(accessRoutes);
        mapRoutes.forEach((it: any) => {
          router.addRoute(it);
        });
        LayoutStore.initPermissionRoute([
          ...asyncRoutes,
          ...constantRoutes,
          ...accessRoutes,
        ]);
        return { ...to, replace: true };
      } else {
        return true;
      }
    }
  }
});

router.afterEach(() => {
  NProgress.done();
});
