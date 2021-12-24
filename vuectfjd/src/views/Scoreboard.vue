<template>
  <el-table
      :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%">
    <el-table-column
        type="index"
        width="100"
        label="Place">
    </el-table-column>
    <el-table-column
        label="Score"
        prop="score">
    </el-table-column>
    <el-table-column
        label="Name"
        prop="username">
    </el-table-column>
    <el-table-column
        align="right">
      <template slot="header" slot-scope="scope">
        <el-input
            v-model="search"
            size="mini"
            placeholder="输入关键字搜索"/>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      search: ''
    }
  },
  methods: {
  },
  created() {
    const _this = this // 备份this指针
    // 使用axios对后端发起访问获取数据
    axios.get("http://localhost:8181/solve/allscores").then(function (resp) {
      console.log(resp)
      _this.tableData = resp.data
    })
  }
}
</script>