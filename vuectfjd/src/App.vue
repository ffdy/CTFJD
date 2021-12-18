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
                     router>
              <!--                     @select="handleSelect" -->
              <el-menu-item index="/Home">CTFJD</el-menu-item>
              <el-menu-item index="/users">Users</el-menu-item>
              <el-menu-item index="/Scoreboard">Scoreboard</el-menu-item>
              <el-menu-item index="/Challenges">Challenges</el-menu-item>
            </el-menu>
          </el-col>

          <el-col :span="8" style="text-align: right; font-size: 12px; border-bottom: solid 1px #e6e6e6">
            <div v-if="login==='1'">
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
                <el-button size="medium" plain>
                  <el-link :underline="false" href="/register">Register<i class="el-icon-plus"></i></el-link>
                </el-button>
                <el-button size="medium" plain>
                  <el-link :underline="false" href="/login">Login<i class="el-icon-user"></i></el-link>
                </el-button>
              </el-button-group>
            </div>

          </el-col>
        </el-row>

      </el-header>

      <el-main>
        <router-view @getUserInfo="setUserInfo"></router-view>
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
      login: 0,
      user: this.$cookies.get("user"),
    }
  },
  methods: {
    handleCommand(command) {
      this.$message('click on item ' + command);
      const _this = this
      if (command === 'logout') {
        axios.get("http://localhost:8181/user/logout").then(function (resp) {
          console.log(resp)
          _this.$message("logout " + resp.data)
          if(resp.data === "success") {
            this.$cookies.set("login", '0')
            this.login = '0'
            this.$router.push("/")
          }
        })
      } else {
        this.$router.push(command)
      }
    },
    setUserInfo(msg) {
      this.login = this.$cookies.get("login")
      this.user = this.$cookies.get("user")
    }
  },
  created() {
    if (!this.$cookies.isKey("user")) {
      this.$cookies.set("user", {name: 'CTFJD'})
    }
    this.login = this.$cookies.get("login")
    this.user = this.$cookies.get("user")
  }
};
</script>
