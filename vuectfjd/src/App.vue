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
              <el-dropdown style="height: 60px" @command="handleCommand">
                <i class="el-icon-setting" style="margin-left: 10px; "></i>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="/profile">Profile</el-dropdown-item>
                  <el-dropdown-item command="/settings">Settings</el-dropdown-item>
                  <el-dropdown-item command="logout">Logout</el-dropdown-item>
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
    return {
      login: 1,
      user: {
        id: 1,
        name: 'ffdy',
      },
      activeIndex: '1',
    }
  },
  methods: {
    handleCommand(command) {
      this.$message('click on item ' + command);
      if(command == 'logout') {
        this.login = 0
        this.$router.push("/")
      //  logout
      } else {
        this.$router.push(command)
      }
    }
  }
};
</script>
