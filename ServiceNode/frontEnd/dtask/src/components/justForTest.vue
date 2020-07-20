<template>
	<div>
		<el-table :data="tableData" style="width: 100%">
			<el-table-column prop="username" label="用户名" width="180">
			</el-table-column>
			<el-table-column prop="pwd" label="密码" width="180">
			</el-table-column>
		</el-table>
	</div>
</template>
<style>
</style>

<script>
	export default {
		data() {
			return {
				tableData: [{
					username: 'admin',
					pwd: '123456',
				}, {
					username: 'admin2',
					pwd: '123456',
				}]
			}
		}
	}
</script>
