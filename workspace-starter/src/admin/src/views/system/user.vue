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
              :loading="batchDelLoading"
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
              <span>{{ sexList[scope.row.sex]?.desc }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="用户组" prop="groupName" />
          <el-table-column
            align="center"
            label="最近登录时间"
            prop="lastLoginTime"
            width="160px"
          />
          <el-table-column
            align="center"
            label="最近登录IP"
            prop="lastLoginIp"
            width="130px"
          />
          <el-table-column align="center" label="锁定">
            <template v-slot="scope">
              <el-switch
                v-model="scope.row.locked"
                :loading="scope.row._lockedLoading"
                :before-change="() => onLockedItem(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column align="center" label="启用">
            <template v-slot="scope">
              <el-switch
                v-model="scope.row.enabled"
                :loading="scope.row._enabledLoading"
                :before-change="() => onEnabledItem(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            fixed="right"
            width="200"
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
                plain
                type="warning"
                size="small"
                @click="showPermissionDialog(scope.row)"
                >权限</el-button
              >
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
          ref="editForm"
          :rules="rules"
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
          <el-form-item label-width="100" label="所属用户组" prop="name">
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
              <el-radio :label="true">是</el-radio>
              <el-radio :label="false">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </template>
    </Dialog>
    <Dialog ref="permissionDialog" title="功能权限">
      <template #content>
        <el-tree
          ref="permissionTreeRef"
          :data="permissionTree"
          show-checkbox
          node-key="id"
          :default-expand-all="true"
        >
          <template #default="{ data }">
            <span>{{ data.name }}【{{ data.code }}】</span>
          </template>
        </el-tree>
      </template>
    </Dialog>
  </div>
</template>

<script lang="ts" setup>
import { useDataTable, useLikeSearch } from "@/hooks";
import { onMounted, reactive, ref, nextTick } from "vue";
import { ElMessageBox } from "element-plus";
import {
  listUser,
  updateUser,
  insertUser,
  deleteUserByIds,
} from "@/api/admin/User.api";
import { listGroupTree } from "@/api/admin/Group.api";
import { listPermissionTree } from "@/api/admin/Permission.api";
import {
  savePermissionAcl,
  listPermissionId,
} from "@/api/admin/PermissionAcl.api";
import { UserDTO } from "@/api/admin/models";
import { DEFAULT_USER, PermissionAclType } from "@/api/constant";
import { SEX } from "@/api/dicts";
import useDictStore from "@/store/modules/dict";
import type { DialogType, TableFooter } from "@/components/types";
import { assign } from "lodash";
import { User } from "@/api/models";

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

const rules = {};

const dictStore = useDictStore();
const groupTree = ref<Array<any>>([]);
const isGroupTreeLoaded = ref<boolean>(false);
const defaultUser = assign({ userPass: "" }, DEFAULT_USER);
const userModel = reactive<UserDTO>(assign({}, defaultUser));
const sexList = ref<Array<any>>([]);
const editForm = ref();

const permissionDialog = ref<DialogType>();
const permissionTreeRef = ref();
const permissionTree = ref<Array<any>>([]);
const isPermissionTreeLoaded = ref<boolean>(false);

const batchDelLoading = ref(false);

dictStore.getDict(SEX).then((dict) => {
  sexList.value = dict;
  defaultUser.sex = dict[0].id;
});

const loadGroupTree = async () => {
  if (!isGroupTreeLoaded.value) {
    const { data } = await listGroupTree({ enabled: true });
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
  ElMessageBox.confirm("确定要删除这些用户吗？", "提示").then(() => {
    const ids = selectRows.value?.map((it: any) => it.id);
    batchDelLoading.value = true;
    deleteUserByIds(ids as number[])
      .then(() => {
        selectRows.value = [];
        doRefresh();
      })
      .finally(() => (batchDelLoading.value = false));
  });
}

function onAddItem() {
  loadGroupTree();
  assign(userModel, defaultUser);
  dialogRef.value?.show(() => {
    editForm.value.validate((valid: boolean) => {
      if (valid) {
        dialogRef.value?.showLoading();
        insertUser(userModel)
          .then(() => {
            doRefresh();
            dialogRef.value?.close();
          })
          .finally(() => dialogRef.value?.closeLoading());
      }
    });
  });
}

function onUpdateItem(item: any) {
  loadGroupTree();
  assign(userModel, defaultUser, item);
  dialogRef.value?.show(() => {
    editForm.value.validate((valid: boolean) => {
      if (valid) {
        dialogRef.value?.showLoading();
        updateUser(userModel)
          .then(({ data }) => {
            assign(item, data);
            dialogRef.value?.close();
          })
          .finally(() => dialogRef.value?.closeLoading());
      }
    });
  });
}

function onLockedItem(item: any) {
  item._lockedLoading = true;
  return updateUser(assign(item, { locked: !item.locked }))
    .then(({ data }) => {
      assign(item, data);
    })
    .finally(() => (item._lockedLoading = false));
}

function onEnabledItem(item: any) {
  item._enabledLoading = true;
  return updateUser(assign(item, { enabled: !item.enabled }))
    .then(({ data }) => {
      assign(item, data);
    })
    .finally(() => (item._enabledLoading = false));
}

const loadPermissionTree = async () => {
  if (!isPermissionTreeLoaded.value) {
    const { data } = await listPermissionTree({ enabled: true });
    isPermissionTreeLoaded.value = true;
    permissionTree.value = data;
  }
};

const showPermissionDialog = async (item: User) => {
  await loadPermissionTree();
  permissionDialog.value?.show(() => {
    const data = {
      itemId: item.id,
      type: PermissionAclType.PERSON_PERMISSION.id,
      permissionIds: permissionTreeRef.value.getCheckedKeys(),
    };
    permissionDialog.value?.showLoading();
    savePermissionAcl(data)
      .then(() => {
        permissionDialog.value?.close();
      })
      .finally(() => permissionDialog.value?.close());
  });
  const { data } = await listPermissionId(
    item.id as number,
    PermissionAclType.PERSON_PERMISSION.id
  );
  nextTick(() => permissionTreeRef.value.setCheckedKeys(data));
};

onMounted(() => {
  doRefresh();
  useHeight();
});
</script>
