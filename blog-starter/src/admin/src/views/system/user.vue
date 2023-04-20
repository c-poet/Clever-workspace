<template>
  <div class="main-container">
    <TableBody>
      <template #tableConfig>
        <TableConfig
          v-model:border="tableConfig.border"
          v-model:stripe="tableConfig.stripe"
          @refresh="doRefresh"
        >
          <template #actions>
            <el-button
              type="primary"
              size="small"
              icon="PlusIcon"
              @click="onAddItem"
            >
              添加
            </el-button>
            <el-button
              type="danger"
              size="small"
              icon="DeleteIcon"
              :disabled="selectRows!.length === 0"
              @click="onDeleteItems"
            >
              删除
            </el-button>
          </template>
        </TableConfig>
      </template>
      <template #default>
        <el-table
          ref="tableRef"
          v-loading="tableLoading"
          :data="dataList"
          :header-cell-style="tableConfig.headerCellStyle"
          :size="tableConfig.size"
          :stripe="tableConfig.stripe"
          :border="tableConfig.border"
          :height="tableConfig.height"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="45" fixed="left" />
          <el-table-column
            align="center"
            label="姓名"
            prop="name"
            width="100"
          />
          <el-table-column
            align="center"
            label="昵称"
            prop="nickName"
            width="100"
          />
          <el-table-column
            align="center"
            label="账号"
            prop="username"
            width="100"
          />
          <el-table-column
            align="center"
            label="手机号"
            prop="mobile"
            width="150"
          />
          <el-table-column
            align="center"
            label="邮箱"
            prop="email"
            width="150"
          />
          <el-table-column align="center" label="头像">
            <template #default="scope">
              <el-avatar v-if="scope.row.avatar" :src="scope.row.avatar" />
              <el-avatar v-else>无</el-avatar>
            </template>
          </el-table-column>
          <el-table-column align="center" label="性别" prop="sex">
            <template #default="scope">
              <div class="gender-container flex justify-center align-center">
                <img
                  class="gender-icon"
                  :src="
                    scope.row.sex === 1
                      ? require('@/assets/icon_sex_man.png')
                      : require('@/assets/icon_sex_woman.png')
                  "
                />
                <span>{{ scope.row.sex === 0 ? "男" : "女" }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column align="center" label="用户组" prop="groupName" />
          <el-table-column
            align="center"
            label="上次登录时间"
            prop="lastLoginTime"
            width="160px"
          />
          <el-table-column
            align="center"
            label="上次登录IP"
            prop="lastLoginIp"
            width="130px"
          />
          <el-table-column align="center" label="是否锁定">
            <template #default="scope">
              <el-tag
                size="small"
                :type="scope.row.locked === 1 ? 'success' : 'danger'"
              >
                {{ scope.row.locked === 1 ? "是" : "否" }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" label="是否启用">
            <template #default="scope">
              <el-tag
                size="small"
                :type="scope.row.enabled === 1 ? 'success' : 'danger'"
              >
                {{ scope.row.enabled === 1 ? "是" : "否" }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            fixed="right"
            width="220"
          >
            <template #default="scope">
              <el-button
                type="primary"
                size="small"
                plain
                @click="onUpdateItem(scope.row)"
                >编辑</el-button
              >
              <el-button
                :type="scope.row.status === 1 ? 'warning' : 'success'"
                size="small"
                plain
                @click="onEnableItem(scope.row)"
                >{{ scope.row.locked === 1 ? "解锁" : "锁定" }}</el-button
              >
              <el-button plain type="warning" size="small">权限</el-button>
            </template>
          </el-table-column>
        </el-table>
      </template>
      <template #footer>
        <TableFooter
          ref="tableFooter"
          @refresh="doRefresh"
          @pageChanged="doRefresh"
        />
      </template>
    </TableBody>
    <Dialog ref="dialogRef">
      <template #content>
        <el-form
          class="base-form-container"
          :model="userModel"
          :inline="true"
          label-width="80px"
          label-position="right"
        >
          <el-divider border-style="dashed" content-position="left"
            >基本信息</el-divider
          >
          <el-form-item label="账号" prop="username">
            <el-input
              v-model="userModel.username"
              placeholder="请输入账号"
              clearable
            />
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input
              v-model="userModel.name"
              placeholder="请输入姓名"
              clearable
            />
          </el-form-item>
          <el-form-item label="昵称" prop="nickName">
            <el-input
              v-model="userModel.nickName"
              placeholder="请输入昵称"
              clearable
            />
          </el-form-item>
          <el-form-item label="手机" prop="mobile">
            <el-input
              v-model="userModel.mobile"
              placeholder="请输入手机号码"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input
              v-model="userModel.email"
              placeholder="请输入邮箱"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="userModel.sex">
              <el-radio
                v-for="(item, index) in sexList"
                v-bind:key="index"
                :label="item.id"
                >{{ item.desc }}</el-radio
              >
            </el-radio-group>
          </el-form-item>
          <el-divider border-style="dashed" content-position="left">
            组织设置
          </el-divider>
          <el-form-item
            label-width="100"
            label="所属用户组"
            prop="name"
          >
            <TreeSelector
              v-model:value="userModel.groupId"
              placeholder="请选择所属用户组"
              :data="groupTree"
              :dataFields="{
                label: 'name',
                value: 'id',
              }"
            />
          </el-form-item>
          <el-divider content-position="left">安全设置</el-divider>
          <el-form-item label="登录密码" prop="path">
            <el-input
              v-model="userModel.userPass"
              type="password"
              placeholder="请输入登录密码"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="path">
            <el-input
              v-model="userModel.userPass"
              type="password"
              placeholder="请输入登录密码"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-divider content-position="left">其它信息</el-divider>
          <el-form-item label="是否启用" prop="path">
            <el-radio-group v-model="userModel.enabled">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </template>
    </Dialog>
  </div>
</template>

<script lang="ts" setup>
import { useDataTable } from "@/hooks";
import { onMounted, reactive, ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { listUser } from "@/api/admin/User.api";
import { listGroupTree } from "@/api/admin/Group.api";
import { UserDTO } from "@/api/admin/models";
import { DEFAULT_USER } from "@/api/constant";
import { SEX } from "@/api/dicts";
import useDictStore from "@/store/modules/dict";
import type { DialogType, TableFooter } from "@/components/types";
import { assign } from "lodash";

const dialogRef = ref<DialogType>();
const tableFooter = ref<TableFooter>();
const tableRef = ref();
const {
  dataList,
  tableLoading,
  tableConfig,
  handleSuccess,
  handleSelectionChange,
  selectRows,
  useHeight,
} = useDataTable<UserModelType>();

const dictStore = useDictStore();
const groupTree = ref<Array<any>>([]);
const isGroupTreeLoaded = ref<boolean>(false);
const defaultUser = assign({ userPass: "" }, DEFAULT_USER);
const userModel = reactive<UserDTO>(assign({}, defaultUser));
const sexList = ref<Array<any>>([]);

dictStore.getDict(SEX).then((dict) => {
  sexList.value = dict;
  defaultUser.sex = dict[0].id;
});

const loadGroupTree = async () => {
  if (!isGroupTreeLoaded.value) {
    const { data } = await listGroupTree();
    isGroupTreeLoaded.value = true;
    groupTree.value = data;
  }
};

function doRefresh() {
  const pageNo = tableFooter.value?.pageModel.currentPage;
  const pageSize = tableFooter.value?.pageModel.pageSize;
  listUser({ pageNo, pageSize })
    .then(({ data, total }) => {
      return handleSuccess({ data, totalSize: total });
    })
    .then((res: any) => {
      tableFooter.value?.setTotalSize(res.totalSize);
    });
}

function onDeleteItems() {
  ElMessageBox.confirm("确定要删除这些用户吗？", "提示")
    .then(() => {
      ElMessage.success(
        "数据模拟删除成功, 参数为：" +
          JSON.stringify(selectRows.value?.map((it: any) => it.id))
      );
    })
    .catch(console.log);
}

function onAddItem() {
  loadGroupTree();
  assign(userModel, defaultUser);
  dialogRef.value?.show(() => {
    console.log(userModel);
  });
}

function onUpdateItem(item: any) {
  loadGroupTree();
  assign(userModel, defaultUser, item);
  dialogRef.value?.show(() => {
    dialogRef.value?.showLoading();
    setTimeout(() => {
      ElMessage.success("模拟成功, 参数为：" + JSON.stringify(userModel));
      dialogRef.value?.close();
    }, 2000);
  });
}

function onEnableItem(item: any) {
  ElMessageBox.confirm(
    "确定要" + (item.status === 1 ? "禁用" : "启用") + "此用户吗？",
    "提示"
  )
    .then(() => {
      ElMessage.success(
        "模拟成功, 参数为：" + JSON.stringify({ uid: item.id })
      );
    })
    .catch(console.log);
}

onMounted(() => {
  doRefresh();
  useHeight();
});
</script>

<style lang="scss" scoped>
.gender-container {
  .gender-icon {
    width: 20px;
  }
}
</style>
