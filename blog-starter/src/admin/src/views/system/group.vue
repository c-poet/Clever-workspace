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
              >添加
            </el-button>
          </template>
        </TableConfig>
      </template>
      <template #default>
        <el-table
          v-loading="tableLoading"
          :data="dataList"
          :header-cell-style="tableConfig.headerCellStyle"
          :size="tableConfig.size"
          :stripe="tableConfig.stripe"
          :border="tableConfig.border"
          row-key="id"
          :tree-props="{ children: 'children' }"
        >
          <el-table-column align="center" label="序号" fixed="left" width="150">
            <template v-slot="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column align="center" label="编码" prop="code" />
          <el-table-column align="center" label="名称" prop="name" />
          <el-table-column align="center" label="是否启用">
            <template #default="scope">
              <el-tag :type="scope.row.enabled ? '' : 'danger'" size="small">{{
                scope.row.enabled ? "是" : "否"
              }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            :width="220"
            label="操作"
            fixed="right"
          >
            <template v-slot="scope">
              <el-button
                plain
                type="primary"
                size="small"
                @click="onUpdateItem(scope.row)"
                >编辑</el-button
              >
              <el-button
                plain
                type="danger"
                size="small"
                @click="onDeleteItem(scope.row)"
                >删除</el-button
              >
              <el-button
                plain
                type="warning"
                size="small"
                @click="showPermissionDialog(scope.row)"
                >功能权限</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </template>
    </TableBody>
    <Dialog ref="dialogRef">
      <template #content>
        <el-form
          class="base-form-container"
          ref="editForm"
          :model="editModel"
          :rules="rules"
          label-width="100px"
          label-position="right"
        >
          <el-form-item label="上级用户组">
            <TreeSelector
              v-model:value="editModel.parentId"
              placeholder="请选择上级用户组"
              :data="dataList"
              :dataFields="{
                label: 'name',
                value: 'id',
                children: 'children',
              }"
            />
          </el-form-item>
          <el-form-item label="用户组编码" prop="code">
            <el-input v-model="editModel.code" placeholder="请输入用户组编码" />
          </el-form-item>
          <el-form-item label="用户组名称" prop="name">
            <el-input v-model="editModel.name" placeholder="请输入用户组名称" />
          </el-form-item>
          <el-form-item label="排序值" prop="order">
            <el-input-number
              v-model="editModel.order"
              placeholder="请输入排序值"
              :max="99"
              :min="0"
            />
          </el-form-item>
          <el-form-item label="是否启用">
            <el-switch v-model="editModel.enabled" />
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
          :check-strictly="true"
          node-key="id"
          :default-expanded-keys="defaultExpandedKeys"
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
import {
  deleteGroupById,
  insertGroup,
  listGroupTree,
  updateGroup,
} from "@/api/admin/Group.api";
import { listPermissionTree } from "@/api/admin/Permission.api";
import { listPermissionId } from "@/api/admin/PermissionAcl.api";
import {
  CHECK_REGEX01,
  DEFAULT_GROUP,
  PermissionAclType,
} from "@/api/constant";
import { Group } from "@/api/models";
import type { DialogType } from "@/components/types";
import { useDataTable } from "@/hooks";
import { ElMessageBox } from "element-plus";
import { assign } from "lodash";
import { onMounted, reactive, ref, nextTick } from "vue";

const rules = {
  code: [
    { required: true, message: "请输入用户组编码", trigger: "blur" },
    {
      pattern: CHECK_REGEX01,
      message: "功能编码由数字、字母及下划线组成",
      trigger: "blur",
    },
  ],
  name: [{ required: true, message: "请输入用户组名称", trigger: "blur" }],
};

const defaultModel = assign({}, DEFAULT_GROUP);
const editForm = ref();
const editModel = reactive<Group>(assign({}, defaultModel));

const { tableLoading, tableConfig, dataList, handleSuccess } = useDataTable();
const dialogRef = ref<DialogType>();

const permissionDialog = ref<DialogType>();
const permissionTreeRef = ref();
const permissionTree = ref<Array<any>>([]);
const isPermissionTreeLoaded = ref<boolean>(false);
const defaultExpandedKeys = ref<number[]>([]);

function doRefresh() {
  listGroupTree().then(handleSuccess);
}

function onAddItem() {
  assign(editModel, defaultModel);
  dialogRef.value?.show(() => {
    editForm.value.validate((valid: boolean) => {
      if (valid) {
        insertGroup(editModel).then(() => {
          doRefresh();
          dialogRef.value?.close();
        });
      }
    });
  });
}

function onUpdateItem(item: any) {
  assign(editModel, defaultModel, item);
  dialogRef.value?.show(() => {
    editForm.value.validate((valid: boolean) => {
      if (valid) {
        updateGroup(editModel).then(({ data }) => {
          assign(item, data);
          dialogRef.value?.close();
        });
      }
    });
  });
}

function onDeleteItem(item: any) {
  ElMessageBox.confirm(`确定删除编码为[${item.code}]的用户组？`).then(() => {
    deleteGroupById(item.id).then(() => doRefresh());
  });
}

const loadPermissionTree = async () => {
  if (!isPermissionTreeLoaded.value) {
    const { data } = await listPermissionTree();
    permissionTree.value = data;
    isPermissionTreeLoaded.value = true;
  }
};

const showPermissionDialog = async (item: Group) => {
  await loadPermissionTree();
  permissionDialog.value?.show(() => {
    console.log(permissionTreeRef.value.getCheckedKeys());
  });
  const { data } = await listPermissionId(
    item.id as number,
    PermissionAclType.GROUP_PERMISSION.id
  );
  nextTick(() => permissionTreeRef.value.setCheckedKeys(data));
};

onMounted(doRefresh);
</script>
