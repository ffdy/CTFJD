<template>
  <div align="center" style="margin-top: 150px">
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
      <el-form-item label="User Name" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="pass">
        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

export default {
  data() {
    return {
      ruleForm: {
        name: '',
        pass: '',
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        pass: [
          { required: true, message: '请输入密码',  trigger: 'blur' }
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const user = {
            name: this.ruleForm.name,
            password: this.ruleForm.pass
          }
          axios.post("http://localhost:8181/user/login", user).then(function (resp) {
            console.log(resp)
            if(resp.data != '') {
              _this.$message("login success")
              _this.$cookies.set("login", '1')
              _this.$cookies.set("user", resp.data)
              _this.$emit("getUserInfo", resp.data)
              _this.$router.push("/")
            } else {
              _this.$message("用户名或密码错误")
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>