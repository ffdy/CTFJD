<template>
  <div>
    <div align="center">
      <h1>{{user.name}}</h1>
      <h2>300</h2>
    </div>
    <div style="width: 60%">
      <el-table
          :data="submit"
          height="700px"
          style="width: 100%">
        <el-table-column
            label="Challenge"
            prop="challenge">
        </el-table-column>
        <el-table-column
            label="Submit Time"
            prop="time">
        </el-table-column>
        <el-table-column
            label="Status"
            prop="status">
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
        id: 1,
        name: 'ffdy',
      },
      submit: [
        {
          challenge: 'challenge1',
          status: 'Right',
          time: '2021-12-11'
        }
      ],
    };
  }
}
</script>

<style scoped>

</style>