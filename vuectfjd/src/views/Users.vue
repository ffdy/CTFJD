<template>
  <el-table
      :data="tableData.filter(data => !search
      || data.place.includes(search)
      || data.name.includes(search)
      || data.email.includes(search))"
      style="width: 100%">
    <el-table-column
        label="Name"
        prop="name">
    </el-table-column>
    <el-table-column
        label="Email"
        prop="email">

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
    const _this = this
    axios.get('http://localhost:8181/user/findAll/0').then(function (resp) {
      console.log(resp.data.content)
      _this.tableData = resp.data.content
    })
  }
}
</script>