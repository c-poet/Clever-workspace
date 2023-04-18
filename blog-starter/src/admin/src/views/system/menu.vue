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
          <el-table-column align="center" label="路径" prop="path" />
          <el-table-column align="center" label="图标" prop="id">
            <template #default="scope">
              <el-icon
                v-if="scope.row.icon"
                size="20"
                style="font-size: 16px"
                color="var(--el-color-primary)"
              >
                <component :is="scope.row.icon" />
              </el-icon>
              <div v-else>--</div>
            </template>
          </el-table-column>
          <el-table-column align="center" label="是否缓存">
            <template #default="scope">
              <el-tag
                :type="scope.row.cacheable ? '' : 'danger'"
                size="small"
                >{{ scope.row.cacheable ? "是" : "否" }}</el-tag
              >
            </template>
          </el-table-column>
          <el-table-column align="center" label="是否固定">
            <template #default="scope">
              <el-tag :type="scope.row.affix ? '' : 'danger'" size="small"
                >{{ scope.row.affix ? "是" : "否" }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" label="badge提示">
            <template #default="scope">
              <div class="menu-badge__wrapper">
                <el-badge
                  :value="
                    parseInt(scope.row.badge)
                      ? parseInt(scope.row.badge)
                      : scope.row.badge
                  "
                  :is-dot="scope.row.badge === 'dot'"
                >
                </el-badge>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            :width="200"
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
            </template>
          </el-table-column>
        </el-table>
      </template>
    </TableBody>
    <Dialog ref="dialogRef">
      <template #content>
        <el-form
          class="base-form-container"
          ref="permissionForm"
          :model="permissionModel"
          :rules="rules"
          label-width="80px"
          label-position="right"
        >
          <el-form-item label="上级功能">
            <TreeSelector
              v-model:value="permissionModel.parentId"
              placeholder="请选择上级功能"
              :data="dataList"
              :dataFields="{
                label: 'name',
                value: 'id',
                children: 'children',
              }"
            />
          </el-form-item>
          <el-form-item label="功能编码" prop="code">
            <el-input
              v-model="permissionModel.code"
              placeholder="请输入功能编码"
            />
          </el-form-item>
          <el-form-item label="功能名称" prop="name">
            <el-input
              v-model="permissionModel.name"
              placeholder="请输入功能名称"
            />
          </el-form-item>
          <el-form-item label="功能类型" prop="type">
            <el-radio-group v-model="permissionModel.type">
              <el-radio
                v-for="(item, name) in PermissionType"
                v-bind:key="name"
                :label="item.id"
                >{{ item.desc }}</el-radio
              >
            </el-radio-group>
          </el-form-item>
          <el-form-item label="功能地址" prop="path">
            <el-input
              v-model="permissionModel.path"
              placeholder="请输入功能地址"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="外链地址">
            <el-input
              v-model="permissionModel.url"
              placeholder="请输入外链地址"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="排序值" prop="order">
            <el-input-number
              v-model="permissionModel.order"
              placeholder="请输入排序值"
              :max="99"
              :min="0"
            />
          </el-form-item>
          <el-form-item label="badge提示">
            <el-radio-group v-model="permissionModel.badgeType" size="small">
              <el-radio-button
                v-for="(item, name) in BadgeType"
                v-bind:key="name"
                :label="item.id"
                >{{ item.desc }}</el-radio-button
              >
            </el-radio-group>
            <el-input-number
              v-model="permissionModel.badge"
              v-show="permissionModel.badgeType === BadgeType['NUMBER'].id"
              class="margin-left-sm"
              :max="99"
              :min="1"
            />
          </el-form-item>
          <el-form-item label="功能图标">
            <IconSelector v-model:value="permissionModel.icon" />
          </el-form-item>
          <el-form-item label="是否缓存">
            <el-switch v-model="permissionModel.cacheable" />
          </el-form-item>
          <el-form-item label="是否隐藏">
            <el-switch v-model="permissionModel.hidden" />
          </el-form-item>
          <el-form-item label="是否固定">
            <el-switch v-model="permissionModel.affix" />
          </el-form-item>
          <el-form-item label="是否启用">
            <el-switch v-model="permissionModel.enabled" />
          </el-form-item>
        </el-form>
      </template>
    </Dialog>
  </div>
</template>

<script lang="ts" setup>
import type { DialogType } from "@/components/types";
import { onMounted, reactive, ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { usePost, useDataTable } from "@/hooks";
import {
  listPermissionTree,
  insertPermission,
  updatePermission,
  deletePermissionById,
} from "@/api/admin/Permission.api";
import { BadgeType, PermissionType } from "@/api/constant";
import { Permission } from "@/api/models";
import { assign } from "lodash";

const DEFAULT_PERMISSION = {
  id: null,
  parentId: -1,
  code: "",
  name: "",
  icon: "",
  path: "",
  url: "",
  badgeType: BadgeType["NONE"].id,
  badge: "",
  isSingle: false,
  hidden: false,
  affix: false,
  cacheable: false,
  description: "",
  enabled: true,
  order: 99,
  type: PermissionType["NONE"].id,
};

const rules = {
  parentId: [],
  code: [
    { required: true, message: "请输入功能编码", trigger: "blur" },
    {
      pattern: "^[a-zA-Z0-9_]+$",
      message: "功能编码由数字、字母及下划线组成",
      trigger: "blur",
    },
  ],
};

const permissionForm = ref();
const permissionModel = reactive<Permission>(assign({}, DEFAULT_PERMISSION));

const { tableLoading, tableConfig, dataList, handleSuccess } = useDataTable();
const disableLoad = ref(false);
const dialogRef = ref<DialogType>();

function doRefresh() {
  listPermissionTree().then(handleSuccess);
}

function onAddItem() {
  assign(permissionModel, DEFAULT_PERMISSION);
  dialogRef.value?.show(() => {
    permissionForm.value.validate((valid: boolean) => {
      if (valid) {
        insertPermission(permissionModel).then(() => {
          doRefresh();
          dialogRef.value?.close();
        });
      }
    });
  });
}

function onUpdateItem(item: any) {
  assign(permissionModel, DEFAULT_PERMISSION, item);
  dialogRef.value?.show(() => {
    permissionForm.value.validate((valid: boolean) => {
      if (valid) {
        updatePermission(permissionModel).then(({ data }) => {
          assign(item, data);
          dialogRef.value?.close();
        });
      }
    });
  });
}

function onDeleteItem(item: any) {
  ElMessageBox.confirm(`确定删除编码为[${item.code}]的功能权限？`).then(() => {
    deletePermissionById(item.id).then(() => doRefresh());
  });
}

onMounted(doRefresh);
</script>
<style>
.menu-badge__wrapper sup {
  top: inherit;
}
</style>
<style lang="scss" scoped>
.icon-wrapper {
  list-style: none;
  border: 1px solid #f5f5f5;
  overflow: hidden;
  padding: 0;
  .icon-item {
    float: left;
    width: 25%;
    font-size: 26px;
    border-right: 1px solid #f5f5f5;
    border-bottom: 1px solid #f5f5f5;
    text-align: center;
    cursor: pointer;
    & > div {
      font-size: 12px;
    }
    &:hover {
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
    }
  }
}
</style>
