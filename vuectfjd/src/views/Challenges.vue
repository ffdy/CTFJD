<template>
  <div>
    <el-row>
      <el-col :span="3" v-for="(o, index) in challenges" :key="index" :offset="index%6 >0 ? 1:0">
        <el-button type="primary"
                   style="width: 100%; height: 120px; margin-bottom: 10%"
                   @click="openChallenge(o)">
          <h3>{{o.name}}</h3>
          <h3>{{o.value}}</h3>
          <h3>{{o.category}}</h3>
        </el-button>
        <el-dialog :visible.sync="dialogTableVisible">
          <template>
            <el-tabs v-model="activeName" type="card">
              <el-tab-pane label="Challenge" name="first">
                <div align="center">
                  <h1>{{currentChallenge.name}}</h1>
                  <h2>{{currentChallenge.value}}</h2>
                  <h4>{{currentChallenge.description}}</h4>
                  <el-input style="width: 70%" v-model="flag"></el-input>
                  <el-button @click="submitFlag">提交</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="Solved" name="second">
                <el-table
                    :data="solves"
                    height="300px"
                    style="width: 100%">
                  <el-table-column
                      type="index"
                      width="70"
                      label="Index">
                  </el-table-column>
                  <el-table-column
                      label="Name"
                      prop="name">
                  </el-table-column>
                  <el-table-column
                      label="Email"
                      prop="email">
                  </el-table-column>
                </el-table>
              </el-tab-pane>
            </el-tabs>
          </template>
        </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      challenges: [],
      activeName: 'first',
      dialogTableVisible: false,
      currentChallenge: {},
      solves: [],//数组
      flag: '',
    };
  },
  methods: {
    submitFlag() {
      const _this = this
      if(this.flag!=='') {
        axios.post("http://localhost:8181/flag/check/"+this.currentChallenge.id, this.flag).then(function (resp){
          _this.$message(resp.data)
          if(resp.data === "correct") {
            _this.$router.push("/challenges")
          }
        })
      } else {
        this.$message("flag不能为空")
      }
    },
    openChallenge(o) {
      this.dialogTableVisible = true
      this.currentChallenge = o
      this.activeName = 'first'
      this.flag = ''
      const _this = this
      axios.get("http://localhost:8181/solve/findbychallengeid/"+this.currentChallenge.id).then(function (resp) {
        _this.solves = resp.data
      })
    }
  },
  created() {
    const _this = this
    axios.get("http://localhost:8181/challenge/findAll").then(function (resp) {
      _this.challenges = resp.data
    })
  }
}
</script>