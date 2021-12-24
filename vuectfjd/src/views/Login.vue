<template>
  <div align="center" style="margin-top: 150px">
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px"
             class="demo-ruleForm">
      <el-form-item label="User Name" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="pass">
        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Validate Code" prop="validateCode">
        <el-col :span="16">
          <el-input v-model="ruleForm.validateCode" autocomplete="off" size="60%"></el-input>
        </el-col>
        <el-col :span="8">
          <el-image :src="validateUrl" style="height: 40px;"/>
        </el-col>

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
      validateUrl: 'http://localhost:8181/validate',
      ruleForm: {
        name: '',
        pass: '',
        validateCode: '',
      },
      rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        pass: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        validateCode: [
          {required: true, message: '请输入验证码', trigger: 'blur'}
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
            password: this.ruleForm.pass,
            validate: this.ruleForm.validateCode
          }
          console.log(user)
          axios.post("http://localhost:8181/user/login", user).then(function (resp) {
            console.log(resp)
            if (resp.data != '') {
              _this.$message("login success")
              _this.$cookies.set("login", '1')
              _this.$cookies.set("user", resp.data)
              _this.$emit("getUserInfo", resp.data)
              _this.$router.push("/")
            } else {
              _this.$message("验证码或用户名或密码错误")
              window.location.reload()
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