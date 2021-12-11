<template>
  <div>
    <el-row>
      <el-col :span="3" v-for="(o, index) in challenges" :key="o" :offset="index%6 >0 ? 1:0">
        <el-button type="primary"
                   style="width: 100%; height: 120px; margin-bottom: 10%"
                   @click="dialogTableVisible = true">
          <h3>{{o.name}}</h3>
          <h3>{{o.scores}}</h3>
        </el-button>
        <el-dialog :visible.sync="dialogTableVisible">
          <template>
            <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
              <el-tab-pane label="Challenge" name="first">
                <div align="center">
                  <h1>{{o.name}}</h1>
                  <h2>{{o.scores}}</h2>
                  <h4>{{o.description}}</h4>
                  <el-input style="width: 70%"></el-input>
                  <el-button>asdf</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="Solved" name="second">
                <el-table
                    :data="challenges"
                    height="300px"
                    style="width: 100%">
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

<style>
</style>

<script>
export default {
  data() {
    return {
      challenges: [
        {
          name: 'challenges name',
          description: 'challenge desc',
          scores: '300'
        },
        {
          name: 'challenges name',
          description: 'challenge desc',
          scores: '300'
        },
        {
          name: 'challenges name',
          description: 'challenge desc',
          scores: '300'
        },
        {
          name: 'challenges name',
          description: 'challenge desc',
          scores: '300'
        },
        {
          name: 'challenges name',
          description: 'challenge desc',
          scores: '300'
        },
        {
          name: 'challenges name',
          description: 'challenge desc',
          scores: '300'
        },
        {
          name: 'challenges name',
          description: 'challenge desc',
          scores: '300'
        },
        {
          name: 'challenges name',
          description: 'challenge desc',
          scores: '300'
        },
        {
          name: 'challenges name',
          description: 'challenge desc',
          scores: '300'
        },
        {
          name: 'challenges name',
          description: 'challenge desc',
          scores: '300'
        },
      ],
      activeName: 'first',
      dialogTableVisible: false
    };
  },
  methods: {

  }
}
</script>