<template>
	<div>
		<el-form :label-position="labelPosition">
			<el-card>
				<div slot="header" class="clearfix">
					<span>基本信息</span>
				</div>
				<el-form-item label="子任务名称:">
					<el-input v-model="name" style="width: 300px;"></el-input>
				</el-form-item>

				<el-form-item label="开始时间选择:">
					<el-date-picker v-model="startTime" value-format="yyyy-MM-dd" type="date" placeholder="选择日期">
					</el-date-picker>
				</el-form-item>
				<el-form-item label="截止时间选择:">
					<el-date-picker v-model="deadline" value-format="yyyy-MM-dd" type="date" placeholder="选择日期">
					</el-date-picker>
				</el-form-item>
			</el-card>

			<el-card>
				<div slot="header" class="clearfix">
					<span>更多信息</span>
				</div>
				<el-form-item label="级别:">
					<el-select v-model="level" placeholder="请选择">
						<el-option v-for="item in options1" :key="item.value" :label="item.value" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="状态:">
					<el-select v-model="status" placeholder="请选择">
						<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="标签:">
					<el-tag :key="tag" v-for="tag in tag" closable :disable-transitions="false" @close="handleClose(tag)">
						{{tag}}
					</el-tag>
					<el-input class="input-new-tag" v-if="inputVisible" v-model="inputValue" ref="saveTagInput" size="small"
					@keyup.enter.native="handleInputConfirm" @blur="handleInputConfirm" style="width: 100px;">
					</el-input>
					<el-button v-else class="button-new-tag" size="small" @click="showInput">+添加新标签</el-button>
				</el-form-item>
				<el-form-item label="星级:">
					<el-select v-model="star" placeholder="请选择">
						<el-option v-for="item in options2" :key="item.value" :label="item.label" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
			</el-card>

			<el-card>
				<div slot="header" class="clearfix">
					<span>详细内容</span>
				</div>
			<el-form-item label="子任务内容:">
				<br/>
				<el-input style="width:90%" rows="5" v-model="content" type="textarea"></el-input>
			</el-form-item>
			</el-card>
		</el-form>
		<el-button type="primary" style="margin-top: 15px;margin-right: 10px;background: #24375E;border: 0px ;" icon="el-icon-check"
		 @click="subTaskUpload()">确认上传</el-button>
	</div>
</template>

<script>
	import axios from 'axios';
	import qs from 'qs';
	export default {
		data() {
			return {
				labelPosition:"left",
				tableData: [],
				multipleSelection: [],
				selectedDataTemp: [],
				page: 1,
				role: 0,
				dialogVisible: false,
				taskID: null,
				id: null,
				name: null,
				content: null,
				deadline: null,
				status: null,
				startTime: null,
				level: null,
				tag: [],
				star: null,
				currentPage3: 1,
				stateOperation: null,
				options: [{
						value: 0,
						label: '计划中'
					},
					{
						value: 1,
						label: '执行中'
					},
					{
						value: 2,
						label: '已完成'
					},
					{
						value: 3,
						label: '已取消'
					},
				],
				options1: [{
						value: 0,
					},
					{
						value: 1,
					},
					{
						value: 2,
					},
					{
						value: 3,
					},
				],
				options2: [{
						value: 0,
						label: '一般'
					},
					{
						value: 1,
						label: '较重要'
					},
					{
						value: 2,
						label: '重要'
					},
					{
						value: 3,
						label: '特别重要'
					},
				],
				value: null,
				inputVisible: false,
				inputValue: ''
			}
		},
		methods: {
			subTaskUpload() {
				var a = new URLSearchParams();
				a.append("id", this.id);
				a.append("taskID", this.taskID);
				a.append("name", this.name);
				a.append("content", this.content);
				a.append("deadline", this.deadline);
				a.append("status", this.status);
				a.append("startTime", this.startTime);
				a.append("level", this.level);
				a.append("tag", this.tag);
				a.append("star", this.star);
				if (localStorage.getItem('add')) {
					if (this.name == null || this.content == null || this.deadline == null || this.startTime == null || this.level ==
						null || this.star == null) {
						this.$alert('添加子任务时，除标签外均不能为空', '提示', {
							confirmButtonText: '确定',
						});
					} else {
						a.set('id', 1);
						a.set('taskID', localStorage.getItem('taskID'));
						axios.post('/api/localTask/localSubTask', a, {
								headers: {
									"token": localStorage.getItem("token"),
								}
							})
							.then(res => {
								this.$alert('添加成功', '提示', {
									confirmButtonText: '确定',
									callback: action => {
										localStorage.removeItem('add');
										// 直接跳转到前页会丢失url参数，所以使用返回
										history.go(-1);
										// this.$router.push({
										// 	path: '/SubTask'
										// });
									}
								});

							});
					}
				} else {
					axios.put('/api/localTask/localSubTask', a, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res => {
							this.$alert('修改成功', '提示', {
								confirmButtonText: '确定',
							});
							localStorage.removeItem('taskDetail');
							// 直接跳转到前页会丢失url参数，所以使用返回
							history.go(-1);
							// this.$router.push({
							// 	path: '/SubTask'
							// });
						});
				}
			},
			handleClose(tag) {
				this.tag.splice(this.tag.indexOf(tag), 1);
			},
			showInput() {
				this.inputVisible = true;
				this.$nextTick(_ => {
					this.$refs.saveTagInput.$refs.input.focus();
				});
			},
			handleInputConfirm() {
				let inputValue = this.inputValue;
				if (inputValue) {
					this.tag.push(inputValue);
				}
				this.inputVisible = false;
				this.inputValue = '';
			},
		},
		beforeMount: function() {

			if (localStorage.getItem('add')) {
				localStorage.removeItem('taskDetail');
			} else {
				var taskDetail = JSON.parse(localStorage.getItem('taskDetail'));
				this.taskID = taskDetail.taskID;
				this.id = taskDetail.id;
				this.name = taskDetail.name;
				this.content = taskDetail.content;
				this.deadline = taskDetail.deadline;
				this.status = taskDetail.status;
				this.value = taskDetail.status;
				this.startTime = taskDetail.startTime;
				this.level = taskDetail.level;
				this.tag = taskDetail.tag.replace('{', '').replace('}', '').split(',');
				this.star = taskDetail.star;
			}
		}
	}
</script>

<style>
	.el-tag+.el-tag {
		margin-left: 10px;
	}

	.button-new-tag {
		margin-left: 10px;
		height: 32px;
		line-height: 30px;
		padding-top: 0;
		padding-bottom: 0;
	}

	.input-new-tag {
		width: 90px;
		margin-left: 10px;
		vertical-align: bottom;
	}

	.el-card{
		margin-top: 10px;
	}
</style>
