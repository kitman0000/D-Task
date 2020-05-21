<template>
	<div>
		<label style="margin-left: 10px;">关键词：</label>
		<el-input placeholder="关键词" style="width: 200px;"></el-input>
		<label style="margin-left: 15px;">是否重要：</label>
		<el-select filterable placeholder="请选择状态" ref="statusSelector" v-model="IsImportantValue" style="width: 150px;">
			<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
			</el-option>
		</el-select>
		<label style="margin-left: 15px;">是否已读：</label>
		<el-select filterable placeholder="请选择状态" ref="statusSelector" v-model="hasReadValue" style="width: 150px;">
			<el-option v-for="item in options2" :key="item.value" :label="item.label" :value="item.value">
			</el-option>
		</el-select>

		<label style="margin-left: 15px;">日期选择：</label>
		<el-date-picker v-model="dateValue" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
		</el-date-picker>

		<el-button type="primary" icon="el-icon-search" @click="test01()" style="margin-left: 10px;">搜索</el-button>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				options: [{
					value: true,
					label: '重要信息'
				}, {
					value: false,
					label: '非重要信息'
				}],

				options2: [{
					value: true,
					label: '已读'
				}, {
					value: false,
					label: '未读'
				}],

				IsImportantValue: true,
				hasReadValue: false,
				dateValue:'',
			}
		},

		methods: {
			toggleSelection(rows) {
				if (rows) {
					rows.forEach(row => {
						this.$refs.multipleTable.toggleRowSelection(row);
					});
				} else {
					this.$refs.multipleTable.clearSelection();
				}
			},
			handleSelectionChange(val) {
				this.multipleSelection = val;
			},
			test01(){
				console.log(this.dateValue);
			}
		}
	}
</script>
