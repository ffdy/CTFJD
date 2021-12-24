<template>
  <div>
    <div align="center">
      <h1>{{user.name}}</h1>
      <h2>{{user.score}}</h2>
    </div>
    <div style="width: 60%">
      <el-table
          :data="submit"
          height="700px"
          style="width: 100%">
        <el-table-column
            label="Challenge"
            prop="challengeId">
        </el-table-column>
        <el-table-column
            label="Submit Time"
            prop="date">
        </el-table-column>
        <el-table-column
            label="Status"
            prop="type">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      user: {
        name: this.$cookies.get("user").name,
        score: 0
      },
      submit: [],
    };
  },
  created() {
    const _this = this
    axios.get("http://localhost:8181/solve/myscore").then(function (resp) {
      _this.user.score = resp.data
    })
    axios.get("http://localhost:8181/submission/mysubmission").then(function (resp) {
      _this.submit = resp.data
    })
  }
}
</script>