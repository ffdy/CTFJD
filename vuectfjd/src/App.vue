<template>
  <div>

    <el-container>
      <el-header style="text-align: right; font-size: 12px; line-height: 60px"
                 text-color="#fff"
                 background-color="#545c64">
        <el-row>
          <el-col :span=16>
            <el-menu :default-active="'/Home'"
                     class="el-menu-demo"
                     mode="horizontal"
                     @select="handleSelect" router>
              <el-menu-item index="/Home">CTFJD</el-menu-item>
              <el-menu-item index="/users">Users</el-menu-item>
              <el-menu-item index="/Scoreboard">Scoreboard</el-menu-item>
              <el-menu-item index="/Challenges">Challenges</el-menu-item>
            </el-menu>
          </el-col>

          <el-col :span="8" style="text-align: right; font-size: 12px; border-bottom: solid 1px #e6e6e6">
            <div v-if="login">
              <span style="height: 60px">{{ user.name }}</span>
              <el-dropdown style="height: 60px">
                <i class="el-icon-setting" style="margin-left: 10px; "></i>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item>Settings</el-dropdown-item>
                  <el-dropdown-item>Logout</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            <div v-else>
              <el-button-group router>
<!--                <el-button size="medium" index="/register" plain>Register<i class="el-icon-plus"></i></el-button>-->
<!--                <el-button size="medium" index="/login" plain>Login<i class="el-icon-user"></i></el-button>-->
                <el-button size="medium" plain><el-link :underline="false" href="/register">Register<i class="el-icon-plus"></i></el-link></el-button>
                <el-button size="medium" plain><el-link :underline="false" href="/login">Login<i class="el-icon-user"></i></el-link></el-button>
              </el-button-group>
            </div>

          </el-col>
        </el-row>

      </el-header>

      <el-main>
        <router-view></router-view>
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
      login: 0,
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
