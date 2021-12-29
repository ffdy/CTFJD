<template>
  <div>
    <el-row>
      <el-col :span="5" v-for="(o, index) in challenges" :key="index" :offset="index%4 >0 ? 1:0">
        <el-button type="primary"
                   style="width: 100%; height: 120px; margin-bottom: 10%"
                   @click="openChallenge(o, index)">
          <h3>{{ o.name }}</h3>
          <h3>{{ o.value }}</h3>
          <h3>{{ o.category }}</h3>
        </el-button>
        <el-dialog :visible.sync="dialogTableVisible">
          <template>
            <el-tabs v-model="activeName" type="card"
                     v-loading="(currentImage.loading && (currentChallenge.id === currentImage.id))">
              <el-tab-pane label="Challenge" name="first">
                <div align="center">
                  <h1>{{ currentChallenge.name }}</h1>
                  <h2>{{ currentChallenge.value }}</h2>
                  <h4>{{ currentChallenge.description }}</h4>
                  <div v-if="currentChallenge.type === 'dynamic_docker'">
                    <div v-if="currentImage.loaded && (currentImage.id === currentChallenge.id)">
                      <h3>{{ currentImage.url }}</h3>
                      <el-button type="warning" @click="restartDockerImage" plain>重启靶机</el-button>
                      <el-button type="danger" @click="stopDockerImage" plain>销毁靶机</el-button>
                    </div>
                    <div v-else>
                      <el-button type="success" @click="startDockerImage" plain>启动靶机</el-button>
                    </div>
                  </div>
                  <div v-if="currentFile.id === currentChallenge.id" style="margin-top: 20px;">
                    <el-button icon="el-icon-download" size="small" type="info" v-for="(file, file_index) in currentFile.files"
                               :key="file_index" @click="downloadFile(file)">{{file.name}}
                    </el-button>
                  </div>
                  <div style="margin-top: 20px;">
                    <el-input style="width: 70%" v-model="flag"></el-input>
                    <el-button @click="submitFlag">提交</el-button>
                  </div>
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
      currentImage: {
        id: null,
        url: '',
        loading: false,
        loaded: false,
      },
      currentFile: {
        id: null,
        files: [],
      },
      solves: [],//数组
      flag: '',
    };
  },
  methods: {
    submitFlag() {
      const _this = this
      if (this.flag !== '') {
        axios.post("/api/flag/check/" + this.currentChallenge.id, this.flag).then(function (resp) {
          _this.$message(resp.data)
          if (resp.data === "correct") {
            _this.$router.push("/challenges")
          }
        })
      } else {
        this.$message("flag不能为空")
      }
    },
    openChallenge(o, index) {
      this.dialogTableVisible = true
      this.currentChallenge = o
      this.activeName = 'first'
      this.flag = ''
      const _this = this
      axios.get("/api/solve/findbychallengeid/" + this.currentChallenge.id).then(function (resp) {
        _this.solves = resp.data
      })
      axios.get("/api/challengefile/getinfo/" + this.currentChallenge.id).then(function (resp) {
        _this.currentFile.id = _this.currentChallenge.id
        _this.currentFile.files = resp.data
      })
    },
    startDockerImage() {
      this.currentImage.loading = true
      this.currentImage.id = this.currentChallenge.id
      const _this = this
      axios.get("/api/docker/geturl/" + this.currentChallenge.id).then(function (resp) {
        console.log(resp.data)
        _this.currentImage.loading = false;
        _this.currentImage.loaded = true;
        _this.currentImage.url = resp.data
      })
    },
    restartDockerImage() {
      this.currentImage.loading = true;
      const _this = this
      axios.get("/api/docker/restart").then(function (resp) {
        console.log(resp.data)
        _this.currentImage.loading = false;
      })
    },
    stopDockerImage() {
      this.currentImage.loading = true;
      const _this = this
      axios.get("/api/docker/stop").then(function (resp) {
        console.log(resp.data)
        _this.currentImage.loaded = false;
        _this.currentImage.loading = false;
      })
    },
    downloadFile(file) {
      window.location.href = "/api/challengefile/getfile/" + file.challengeId + "/" +file.name
    },
  },
  created() {
    const _this = this
    axios.get("/api/challenge/findAll").then(function (resp) {
      console.log(resp.data)
      _this.challenges = resp.data
    })
  }
}
</script>