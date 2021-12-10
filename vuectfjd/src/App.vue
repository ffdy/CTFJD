<template>
  <div>

    <el-container>
      <el-header style="text-align: right; font-size: 12px; line-height: 60px"
                 text-color="#fff"
                 background-color="#545c64">
        <el-row>
          <el-col :span=16>
            <el-menu :default-active="activeIndex"
                     class="el-menu-demo"
                     mode="horizontal"
                     @select="handleSelect">
              <el-menu-item index="1">CTFJD</el-menu-item>
              <el-menu-item index="2">Users</el-menu-item>
              <el-menu-item index="3">Scoreboard</el-menu-item>
              <el-menu-item index="4">Challenges</el-menu-item>
            </el-menu>
          </el-col>

          <el-col :span="8" style="text-align: right; font-size: 12px; border-bottom: solid 1px #e6e6e6">
            <div v-if="login">
              <span style="height: 60px">{{ user.name }}</span>
              <el-dropdown style="height: 60px">
                <i class="el-icon-setting" style="margin-left: 10px; "></i>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item>查看</el-dropdown-item>
                  <el-dropdown-item>新增</el-dropdown-item>
                  <el-dropdown-item>删除</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            <div v-else>
              <el-button-group>
                <el-button size="medium" plain>Register<i class="el-icon-plus"></i></el-button>
                <el-button size="medium" plain>Login<i class="el-icon-user"></i></el-button>
              </el-button-group>
            </div>

          </el-col>
        </el-row>

      </el-header>

      <el-main>
        <el-table :data="tableData">
          <el-table-column prop="date" label="日期" width="140">
          </el-table-column>
          <el-table-column prop="name" label="姓名" width="120">
          </el-table-column>
          <el-table-column prop="address" label="地址">
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<style>
</style>

<script>
export default {
  data() {
    const item = {
      date: '2016-05-02',
      name: '王小虎',
      address: '上海市普陀区金沙江路 1518 弄'
    };
    return {
      login: 1,
      user: {
        id: 1,
        name: 'ffdy',
      },
      tableData: Array(20).fill(item),
      activeIndex: '1',
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  }
};
</script>
