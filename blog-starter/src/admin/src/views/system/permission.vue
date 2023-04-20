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
          ref="editForm"
          :model="editModel"
          :rules="rules"
          label-width="80px"
          label-position="right"
        >
          <el-form-item label="上级功能">
            <TreeSelector
              v-model:value="editModel.parentId"
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
            <el-input v-model="editModel.code" placeholder="请输入功能编码" />
          </el-form-item>
          <el-form-item label="功能名称" prop="name">
            <el-input v-model="editModel.name" placeholder="请输入功能名称" />
          </el-form-item>
          <el-form-item label="功能类型" prop="type">
            <el-radio-group v-model="editModel.type">
              <el-radio
                v-for="(item, name) in permissionTypes"
                v-bind:key="name"
                :label="item.id"
                >{{ item.desc }}</el-radio
              >
            </el-radio-group>
          </el-form-item>
          <el-form-item label="功能地址" prop="path">
            <el-input v-model="editModel.path" placeholder="请输入功能地址">
            </el-input>
          </el-form-item>
          <el-form-item label="外链地址">
            <el-input v-model="editModel.url" placeholder="请输入外链地址">
            </el-input>
          </el-form-item>
          <el-form-item label="排序值" prop="order">
            <el-input-number
              v-model="editModel.order"
              placeholder="请输入排序值"
              :max="99"
              :min="0"
            />
          </el-form-item>
          <el-form-item label="badge提示">
            <el-radio-group v-model="editModel.badgeType" size="small">
              <el-radio-button
                v-for="(item, name) in badgeTypes"
                v-bind:key="name"
                :label="item.id"
                >{{ item.desc }}</el-radio-button
              >
            </el-radio-group>
            <el-input-number
              v-model="editModel.badge"
              v-show="editModel.badgeType === 4"
              class="margin-left-sm"
              :max="99"
              :min="1"
            />
          </el-form-item>
          <el-form-item label="功能图标">
            <IconSelector v-model:value="editModel.icon" />
          </el-form-item>
          <el-form-item label="是否缓存">
            <el-switch v-model="editModel.cacheable" />
          </el-form-item>
          <el-form-item label="是否隐藏">
            <el-switch v-model="editModel.hidden" />
          </el-form-item>
          <el-form-item label="是否固定">
            <el-switch v-model="editModel.affix" />
          </el-form-item>
          <el-form-item label="是否启用">
            <el-switch v-model="editModel.enabled" />
          </el-form-item>
        </el-form>
      </template>
    </Dialog>
  </div>
</template>

<script lang="ts" setup>
import {
  deletePermissionById,
  insertPermission,
  listPermissionTree,
  updatePermission,
} from "@/api/admin/Permission.api";
import { BADGE_TYPE, PERMISSION_TYPE } from "@/api/dicts";
import { DEFAULT_PERMISSION, CHECK_REGEX01 } from "@/api/constant";
import { Permission } from "@/api/models";
import type { DialogType } from "@/components/types";
import { useDataTable } from "@/hooks";
import useDictStore from "@/store/modules/dict";
import { ElMessageBox } from "element-plus";
import { assign } from "lodash";
import { onMounted, reactive, ref } from "vue";

const rules = {
  code: [
    { required: true, message: "请输入功能编码", trigger: "blur" },
    {
      pattern: CHECK_REGEX01,
      message: "功能编码由数字、字母及下划线组成",
      trigger: "blur",
    },
  ],
};

const defaultModel = assign({}, DEFAULT_PERMISSION);
const dictStore = useDictStore();
const editForm = ref();
const editModel = reactive<Permission>(assign({}, defaultModel));
const permissionTypes = ref<Array<any>>([]);
const badgeTypes = ref<Array<any>>([]);
const { tableLoading, tableConfig, dataList, handleSuccess } = useDataTable();
const dialogRef = ref<DialogType>();

dictStore.getDict(PERMISSION_TYPE).then((data) => {
  permissionTypes.value = data;
  defaultModel.type = permissionTypes.value[0].id;
});

dictStore.getDict(BADGE_TYPE).then((data) => {
  badgeTypes.value = data;
  defaultModel.badgeType = badgeTypes.value[0].id;
});

function doRefresh() {
  listPermissionTree().then(handleSuccess);
}

function onAddItem() {
  assign(editModel, defaultModel);
  dialogRef.value?.show(() => {
    editForm.value.validate((valid: boolean) => {
      if (valid) {
        insertPermission(editModel).then(() => {
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
        updatePermission(editModel).then(({ data }) => {
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
